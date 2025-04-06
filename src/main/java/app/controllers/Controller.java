package app.controllers;

import app.daos.IDAO;

import java.util.List;

public class Controller<T> implements IController<T> {

    protected final IDAO<T> dao;

    public Controller(IDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public T create(T dto) {
        return dao.create(dto);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public T getById(int id) {
        return dao.getById(id);
    }

    @Override
    public T update(T dto) {
        return dao.update(dto);
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
