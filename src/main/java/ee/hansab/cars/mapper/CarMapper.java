package ee.hansab.cars.mapper;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.model.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarMapper {
    public static CarDto toDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .numberplate(car.getNumberplate())
                .user(car.getUser().getName())
                .build();
    }
}
