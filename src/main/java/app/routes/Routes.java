package app.routes;


import app.controllers.SkiLessonController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    private SkiLessonRoutes skiLessonRoutes = new SkiLessonRoutes();

    public Routes() {

    }

    public EndpointGroup getRoutes() {
        return () -> {
            path("/ski-lessons", skiLessonRoutes.getRoutes());
        };
    }
}
