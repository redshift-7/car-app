package ee.hansab.cars.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private List<CarDto> cars;
}