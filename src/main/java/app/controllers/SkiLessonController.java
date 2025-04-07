package app.controllers;

import app.config.HibernateConfig;
import app.daos.SkiLessonDAO;
import app.dtos.InstructorOverviewDTO;
import app.dtos.InstructorTimeDTO;
import app.dtos.SkiInstructionsDTO;
import app.dtos.SkiLessonDTO;
import app.entities.SkiLesson;
import app.enums.Level;
import app.exceptions.ApiException;
import app.exceptions.ErrorResponse;
import app.utils.SkiInstructionsAPI;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SkiLessonController implements IController<SkiLessonDTO> {

    private static final Logger logger = LoggerFactory.getLogger(SkiLessonController.class);
    private final SkiLessonDAO skiLessonDAO;

    public SkiLessonController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.skiLessonDAO = SkiLessonDAO.getInstance(emf);
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SkiLesson lesson = skiLessonDAO.getByIdRaw(id);
            if (lesson == null) {
                ctx.status(404).result("Ski-lektion ikke fundet.");
                return;
            }
            SkiLessonDTO dto = new SkiLessonDTO(lesson);
            dto.setInstructions(SkiInstructionsAPI.getInstructionsByLevel(lesson.getLevel()));
            ctx.json(dto);

        } catch (Exception e) {
            logger.error("Fejl ved hentning af ski-lektion", e);
            ctx.status(500).result("Intern serverfejl");
        }
    }


    @Override
    public void getAll(Context ctx) {
        try {
            List<SkiLessonDTO> skiLessons = skiLessonDAO.getAll();
            ctx.json(skiLessons);
        } catch (Exception e) {
            logger.error("Fejl ved hentning af alle skilektioner", e);
            ctx.status(500).result("Der opstod en intern fejl ved hentning af skilektionerne.");
        }
    }

    @Override
    public void create(Context ctx) {
        try {
            SkiLessonDTO skiLessonDTO = ctx.bodyAsClass(SkiLessonDTO.class);
            SkiLessonDTO createdSkiLesson = skiLessonDAO.create(skiLessonDTO);
            if (createdSkiLesson != null) {
                ctx.status(201).json(createdSkiLesson);
            } else {
                logger.warn("Kunne ikke oprette skilektion: {}", skiLessonDTO);
                ctx.status(400).result("Kunne ikke oprette skilektion. Tjek at alle nødvendige data er inkluderet.");
            }
        } catch (Exception e) {
            logger.error("Fejl ved oprettelse af skilektion", e);
            ctx.status(500).result("Der opstod en intern fejl ved oprettelsen af skilektionen.");
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            SkiLessonDTO skiLessonDTO = ctx.bodyAsClass(SkiLessonDTO.class);
            SkiLessonDTO updatedSkiLesson = skiLessonDAO.update(id, skiLessonDTO);
            if (updatedSkiLesson != null) {
                ctx.json(updatedSkiLesson);
            } else {
                logger.warn("Opdatering mislykkedes: Ski-lektion med id {} blev ikke fundet eller kunne ikke opdateres.", id);
                ctx.status(404).result("Ski-lektion med id " + id + " blev ikke fundet eller kunne ikke opdateres.");
            }
        } catch (NumberFormatException e) {
            logger.error("Ugyldigt id format ved opdatering af ski-lektion med id {}", ctx.pathParam("id"), e);
            ctx.status(400).result("Ugyldigt id format. Id skal være et heltal.");
        } catch (Exception e) {
            logger.error("Fejl ved opdatering af ski-lektion med id {}", ctx.pathParam("id"), e);
            ctx.status(500).result("Der opstod en intern fejl ved opdateringen af skilektionen.");
        }
    }

    @Override
    public boolean delete(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean exists = skiLessonDAO.validatePrimaryKey(id);
            if (exists) {
                skiLessonDAO.delete(id);
                ctx.status(204).result("Skilektion slettet");
            } else {
                throw new ApiException(404, "Ski-lektion med id " + id + " blev ikke fundet.");
            }
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Ugyldigt id format. Id skal være et heltal.");
        } catch (ApiException e) {
            // Returner fejlinformation som JSON
            ctx.status(e.getStatusCode()).json(new ErrorResponse(e.getMessage(), e.getStatusCode()));
        } catch (Exception e) {
            throw new ApiException(500, "Der opstod en intern fejl ved sletningen af skilektionen.");
        }
        return true;
    }


    public void addInstructorToLesson(Context ctx) {
        try {
            // Hent lessonId og instructorId fra URL
            int lessonId = Integer.parseInt(ctx.pathParam("lessonId"));
            int instructorId = Integer.parseInt(ctx.pathParam("instructorId"));

            // Kald metoden, der tilføjer instruktøren til skilektionen
            skiLessonDAO.addInstructorToSkiLesson(lessonId, instructorId);

            ctx.status(200).result("Instruktør tilføjet til ski-lektion.");
        } catch (Exception e) {
            logger.error("Fejl ved tilføjelse af instruktør til skilektion", e);
            ctx.status(500).result("Der opstod en intern fejl.");
        }
    }


    @Override
    public boolean validatePrimaryKey(int id) {
        return skiLessonDAO.validatePrimaryKey(id);
    }

    @Override
    public SkiLessonDTO validateEntity(Context ctx) {
        try {
            return ctx.bodyAsClass(SkiLessonDTO.class);
        } catch (Exception e) {
            logger.error("Fejl ved validering af ski-lektion data", e);
            return null;
        }
    }

    public void filterByLevel(Context ctx) {
        try {
            String levelParam = ctx.queryParam("level");
            if (levelParam == null) {
                ctx.status(400).result("Mangler query parameter 'level'.");
                return;
            }
            Level level = Level.valueOf(levelParam.toUpperCase());
            // Enten kaldes en DAO-metode eller filter i controlleren
            List<SkiLessonDTO> lessons = skiLessonDAO.getLessonsByLevel(level);
            ctx.json(lessons);
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Ugyldig level parameter.");
        } catch (Exception e) {
            logger.error("Fejl ved filtrering af skilektioner efter level", e);
            ctx.status(500).result("Der opstod en intern fejl ved filtrering af skilektioner.");
        }
    }

    public void getInstructorOverview(Context ctx) {
        try {
            List<InstructorOverviewDTO> overview = skiLessonDAO.getTotalPriceByInstructor();
            ctx.json(overview);
        } catch (Exception e) {
            logger.error("Fejl ved hentning af instruktøroversigt", e);
            ctx.status(500).result("Der opstod en intern fejl ved hentning af instruktøroversigt.");
        }
    }

    public void getTotalTimeByInstructor(Context ctx) {
        try {
            List<InstructorTimeDTO> result = skiLessonDAO.getTotalTimeByInstructor();
            ctx.json(result);
        } catch (Exception e) {
            logger.error("Fejl ved hentning af total tid pr. instruktør", e);
            ctx.status(500).result("Der opstod en intern fejl ved hentning af total tid.");
        }
    }

    public void getTotalInstructionDuration(Context ctx) {
        try {
            String levelParam = ctx.queryParam("level");
            if (levelParam == null) {
                ctx.status(400).result("Mangler query parameter 'level'");
                return;
            }

            Level level = Level.valueOf(levelParam.toUpperCase());
            List<SkiInstructionsDTO> instructions = SkiInstructionsAPI.getInstructionsByLevel(level);
            int totalMinutes = instructions.stream().mapToInt(SkiInstructionsDTO::getDurationMinutes).sum();

            ctx.json(Map.of("level", level.name(), "totalMinutes", totalMinutes));
        } catch (IllegalArgumentException e) {
            ctx.status(400).result("Ugyldigt niveau.");
        } catch (Exception e) {
            logger.error("Fejl ved beregning af instruktionstid", e);
            ctx.status(500).result("Intern serverfejl");
        }
    }


}
