package app.entities;

import app.dtos.SkiLessonDTO;
import app.enums.Level;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SkiLesson {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
    private double price;
    private Level level;


    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;

    public SkiLesson(SkiLessonDTO skiLessonDTO) {
        this.name = skiLessonDTO.getName();
        this.startTime = skiLessonDTO.getStartTime();
        this.endTime = skiLessonDTO.getEndTime();
        this.latitude = skiLessonDTO.getLatitude();
        this.longitude = skiLessonDTO.getLongitude();
        this.price = skiLessonDTO.getPrice();
        this.level = skiLessonDTO.getLevel();

        if (skiLessonDTO.getInstructor() != null) {
            this.instructor = new Instructor(skiLessonDTO.getInstructor());
        }
    }

    public SkiLesson() {

    }
}
