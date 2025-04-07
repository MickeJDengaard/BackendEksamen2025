package app.dtos;

import app.entities.SkiLesson;
import app.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiLessonDTO {
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
    private double price;
    private Level level;
    private InstructorDTO instructor;
    private List<SkiInstructionsDTO> instructions;


    public SkiLessonDTO(SkiLesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.startTime = lesson.getStartTime();
        this.endTime = lesson.getEndTime();
        this.latitude = lesson.getLatitude();
        this.longitude = lesson.getLongitude();
        this.price = lesson.getPrice();
        this.level = lesson.getLevel();
        if (lesson.getInstructor() != null) {
            this.instructor = new InstructorDTO(lesson.getInstructor());

        }
    }
}
