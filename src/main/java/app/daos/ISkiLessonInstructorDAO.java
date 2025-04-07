package app.daos;

import app.dtos.SkiLessonDTO;

import java.util.List;
import java.util.Set;

public interface ISkiLessonInstructorDAO<T> {
    T create(T dto);
    List<T> getAll();
    T getById(int id);
    T update(int id, T dto);
    boolean delete(int id);
    void addInstructorToSkiLesson(int lessonId, int instructorId);
    Set<SkiLessonDTO> getSkiLessonsByInstructor(int instructorId);
    boolean validatePrimaryKey(int id);

}
