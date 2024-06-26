package ee.hansab.cars.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private Long id;
    private String make;
    private String model;
    private String numberplate;
    private String user;
}
