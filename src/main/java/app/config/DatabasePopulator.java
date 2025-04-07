package app.config;
/*
import app.daos.InstructorDAO;
import app.daos.SkiLessonDAO;
import app.dtos.InstructorDTO;
import app.dtos.SkiLessonDTO;
import app.enums.Level;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

public class DatabasePopulator {

 public static void populate() {
  EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
  InstructorDAO instructorDAO = InstructorDAO.getInstance(emf);
  SkiLessonDAO skiLessonDAO = SkiLessonDAO.getInstance(emf);

  // Opret og gem instruktører
  InstructorDTO createdInstructor1 = instructorDAO.create(
          new InstructorDTO(0, "Anna", "Skiguide", "anna@ski.com", "12345678", 5)
  );
  InstructorDTO createdInstructor2 = instructorDAO.create(
          new InstructorDTO(0, "Lars", "Snowman", "lars@snow.com", "87654321", 10)
  );

  // Kontrollér om ID'er er korrekt tildelt
  System.out.println("Created Instructor 1 ID: " + createdInstructor1.getId());
  System.out.println("Created Instructor 2 ID: " + createdInstructor2.getId());

  // Hent dem igen via deres ID for at sikre de er managed og har korrekt ID
  InstructorDTO instructor1 = instructorDAO.getById(createdInstructor1.getId());
  InstructorDTO instructor2 = instructorDAO.getById(createdInstructor2.getId());

  // Kontrollér om instruktorerne er hentet korrekt
  if (instructor1 == null || instructor2 == null) {
   System.out.println("Error: One or both instructors not found.");
   return; // Stop videre udførelse, hvis instruktører ikke er fundet
  }

  // Opret skilektioner uden instruktør
  SkiLessonDTO lesson1 = new SkiLessonDTO(
          0,
          "Begynderlektion",
          LocalDateTime.now().plusDays(1),
          LocalDateTime.now().plusDays(1).plusHours(2),
          55.6761, 12.5683,
          300.0,
          Level.BEGINNER,
          null // Ingen instruktør tilknyttet endnu
  );

  SkiLessonDTO lesson2 = new SkiLessonDTO(
          0,
          "Ekspert-session",
          LocalDateTime.now().plusDays(2),
          LocalDateTime.now().plusDays(2).plusHours(3),
          46.8182, 8.2275,
          600.0,
          Level.ADVANCED,
          null // Ingen instruktør tilknyttet endnu
  );

  // Gem skilektionerne først
  lesson1 = skiLessonDAO.create(lesson1); // Gem lektion og få id tilbage
  lesson2 = skiLessonDAO.create(lesson2); // Gem lektion og få id tilbage

  // Tilknyt instruktører bagefter
  skiLessonDAO.addInstructorToSkiLesson(lesson1.getId(), instructor1.getId());
  skiLessonDAO.addInstructorToSkiLesson(lesson2.getId(), instructor2.getId());

  System.out.println("Database populated successfully!");
 }


 public static void main(String[] args) {
  populate();
 }
}*/
