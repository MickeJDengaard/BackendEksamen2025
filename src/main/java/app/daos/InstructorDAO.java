package app.daos;

import app.dtos.InstructorDTO;
import app.entities.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class InstructorDAO implements IDAO<InstructorDTO>{

    private static EntityManagerFactory emf;
    private static InstructorDAO instance;

    public static InstructorDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new InstructorDAO();
        }
        return instance;
    }

    @Override
    public InstructorDTO create(InstructorDTO instructorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = new Instructor(instructorDTO);
            em.persist(instructor);
            em.getTransaction().commit();
            return new InstructorDTO(instructor);
        }
    }


    @Override
    public List<InstructorDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<InstructorDTO> query = em.createQuery(
                    "SELECT new app.dtos.InstructorDTO(i) FROM Instructor i", InstructorDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public InstructorDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Instructor instructor = em.find(Instructor.class, id);
            return instructor != null ? new InstructorDTO(instructor) : null;
        }
    }

    @Override
    public InstructorDTO update(InstructorDTO instructorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = em.find(Instructor.class, instructorDTO.getId());
            if (instructor != null) {
                instructor.setFirstname(instructorDTO.getFirstname());
                instructor.setLastname(instructorDTO.getLastname());
                instructor.setEmail(instructorDTO.getEmail());
                instructor.setPhone(instructorDTO.getPhone());
                instructor.setYearsOfExperience(instructorDTO.getYearsOfExperience());
                Instructor updated = em.merge(instructor);
                em.getTransaction().commit();
                return new InstructorDTO(updated);
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = em.find(Instructor.class, id);
            if (instructor != null) {
                em.remove(instructor);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean validatePrimaryKey(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Instructor.class, id) != null;
        }
    }
}
