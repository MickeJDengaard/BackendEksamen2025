package app.daos;

import app.config.HibernateConfig;
import app.dtos.InstructorDTO;
import app.dtos.InstructorOverviewDTO;
import app.dtos.InstructorTimeDTO;
import app.dtos.SkiLessonDTO;
import app.entities.Instructor;
import app.entities.SkiLesson;
import app.enums.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SkiLessonDAO implements ISkiLessonInstructorDAO<SkiLessonDTO> {

    private static EntityManagerFactory emf;
    private static SkiLessonDAO instance;

    public static SkiLessonDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SkiLessonDAO();
        }
        return instance;
    }

    @Override
    public SkiLessonDTO create(SkiLessonDTO skiLessonDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson lesson = new SkiLesson(skiLessonDTO);
            em.persist(lesson);
            em.getTransaction().commit();
            // Return DTO after persisting entity with correct id
            return new SkiLessonDTO(lesson); // korrekt DTO med id
        }
    }


    @Override
    public List<SkiLessonDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkiLessonDTO> query = em.createQuery("SELECT new app.dtos.SkiLessonDTO(s) FROM SkiLesson s", SkiLessonDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public SkiLessonDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            SkiLesson lesson = em.find(SkiLesson.class, id);
            return new SkiLessonDTO(lesson);
        }
    }

    @Override
    public SkiLessonDTO update(int id, SkiLessonDTO skiLessonDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson lesson = em.find(SkiLesson.class, id);
            if (lesson != null) {
                lesson.setName(skiLessonDTO.getName());
                lesson.setStartTime(skiLessonDTO.getStartTime());
                lesson.setEndTime(skiLessonDTO.getEndTime());
                lesson.setPrice(skiLessonDTO.getPrice());
                lesson.setLevel(skiLessonDTO.getLevel());
                SkiLesson updatedLesson = em.merge(lesson);
                em.getTransaction().commit();
                return new SkiLessonDTO(updatedLesson);
            }
            return null;

        }


    }

    @Override
    public boolean delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson lesson = em.find(SkiLesson.class, id);
            if (lesson != null) {
                em.remove(lesson);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().commit();
            return false;
        }
    }


    @Override
    public boolean validatePrimaryKey(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            SkiLesson lesson = em.find(SkiLesson.class, id);
            return lesson != null;
        }
    }

    @Override
    public Set<SkiLessonDTO> getSkiLessonsByInstructor(int instructorId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkiLessonDTO> query = em.createQuery("SELECT new app.dtos.SkiLessonDTO(s) FROM SkiLesson s WHERE s.instructor.id = :instructorId", SkiLessonDTO.class);
            query.setParameter("instructorId", instructorId);
            return query.getResultList().stream().collect(Collectors.toSet());
        }
    }

    public void addInstructorToSkiLesson(int lessonId, int instructorId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Hent ski-lektionen og instruktøren
            SkiLesson lesson = em.find(SkiLesson.class, lessonId);
            Instructor instructor = em.find(Instructor.class, instructorId);

            if (lesson != null && instructor != null) {
                lesson.setInstructor(instructor); // Tilknyt instruktøren
                em.merge(lesson); // Gem ændringen i ski-lektionen
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<SkiLessonDTO> getLessonsByLevel(Level level) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkiLessonDTO> query = em.createQuery("SELECT new app.dtos.SkiLessonDTO(s) FROM SkiLesson s WHERE s.level = :level", SkiLessonDTO.class);
            query.setParameter("level", level);
            return query.getResultList();
        }
    }

    public List<InstructorOverviewDTO> getTotalPriceByInstructor() {
        try (EntityManager em = emf.createEntityManager()) {
            List<SkiLesson> lessons = em.createQuery("FROM SkiLesson", SkiLesson.class).getResultList();
            // Gruppér efter instructor id og summer prisen
            Map<Integer, Double> map = lessons.stream().filter(l -> l.getInstructor() != null).collect(Collectors.groupingBy(l -> l.getInstructor().getId(), Collectors.summingDouble(SkiLesson::getPrice)));
            return map.entrySet().stream().map(e -> new InstructorOverviewDTO(e.getKey(), e.getValue())).collect(Collectors.toList());
        }
    }

    public List<InstructorTimeDTO> getTotalTimeByInstructor() {
        try (EntityManager em = emf.createEntityManager()) {
            List<SkiLesson> lessons = em.createQuery("FROM SkiLesson", SkiLesson.class).getResultList();

            // Gruppér lektioner efter instruktør
            Map<Integer, List<SkiLesson>> grouped = lessons.stream().filter(l -> l.getInstructor() != null).collect(Collectors.groupingBy(l -> l.getInstructor().getId()));

            return grouped.entrySet().stream().map(entry -> {
                List<SkiLesson> instrLessons = entry.getValue();
                double totalHours = instrLessons.stream().mapToDouble(l -> java.time.Duration.between(l.getStartTime(), l.getEndTime()).toMinutes() / 60.0).sum();

                InstructorDTO instructorDTO = new InstructorDTO(instrLessons.get(0).getInstructor());
                return new InstructorTimeDTO(instructorDTO, totalHours);
            }).collect(Collectors.toList());
        }
    }

    public SkiLesson getByIdRaw(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(SkiLesson.class, id);
        }
    }

}
