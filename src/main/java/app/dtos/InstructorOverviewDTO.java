package app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorOverviewDTO {
    private int instructorId;
    private double totalPrice;
}
