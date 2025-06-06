package app.daos;

import java.util.List;

public interface IDAO<T> {
    T create(T dto);
    List<T> getAll();
    T getById(int id);
    T update(T dto);
    boolean delete(int id);
    boolean validatePrimaryKey(int id);
}