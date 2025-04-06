package app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Trip {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private String startposition;
    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private TripCategory category;

    @ManyToOne
    private Guide guide;  // Mange trips kan have én guide

}
