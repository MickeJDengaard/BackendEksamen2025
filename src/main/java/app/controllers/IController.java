package app.controllers;

import io.javalin.http.Context;

import java.util.List;

public interface IController<T> {
    void create(Context ctx);
    void getAll(Context ctx);
    void getById(Context ctx);
    void update(Context ctx);
    boolean delete(Context ctx);
    T validateEntity(Context ctx);
    boolean validatePrimaryKey(int id);

}
