package app.entities;

import app.dtos.InstructorDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int yearsOfExperience;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Set<SkiLesson> skiLessons;

    public Instructor() {
        // Tom konstruktør
    }

    public Instructor(InstructorDTO instructorDTO) {
        this.id = instructorDTO.getId();
        this.firstname = instructorDTO.getFirstname();
        this.lastname = instructorDTO.getLastname();
        this.email = instructorDTO.getEmail();
        this.phone = instructorDTO.getPhone();
        this.yearsOfExperience = instructorDTO.getYearsOfExperience();
    }
}
