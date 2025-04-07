package app.routes;

import app.controllers.SkiLessonController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SkiLessonRoutes {

    private SkiLessonController skiLessonController = new SkiLessonController();

    public SkiLessonRoutes() {
    }

    protected EndpointGroup getRoutes() {
        return () -> {
            get(skiLessonController::getAll);                // GET /api/ski-lessons
            post(skiLessonController::create);                 // POST /api/ski-lessons
            // Filtrering efter level, fx: GET /api/ski-lessons/filter?level=BEGINNER
            get("/filter", skiLessonController::filterByLevel);

            // Oversigt over instruktører, fx: GET /api/ski-lessons/instructor-overview
            get("/instructor-overview", skiLessonController::getInstructorOverview);
            get("/instructors/total-time", skiLessonController::getTotalTimeByInstructor);


            path("/{id}", () -> {
                get(skiLessonController::getById);             // GET /api/ski-lessons/{id}
                put(skiLessonController::update);              // PUT /api/ski-lessons/{id}
                delete(skiLessonController::delete);           // DELETE /api/ski-lessons/{id}
                get("/instruction-duration", skiLessonController::getTotalInstructionDuration);

            });
            // Rute til at tilføje en instruktør til en ski-lektion:
            put("/{lessonId}/instructors/{instructorId}", skiLessonController::addInstructorToLesson);
        };
    }
}
