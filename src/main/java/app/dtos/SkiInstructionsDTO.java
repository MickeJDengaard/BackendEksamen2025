package app.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SkiInstructionsDTO {
    private String title;
    private String description;
    private String level;
    private int durationMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
