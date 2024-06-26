package ee.hansab.cars.mapper;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.dto.UserDto;
import ee.hansab.cars.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto toDto(User user) {
        List<CarDto> carDtos = user.getCars().stream().map(CarMapper::toDto).toList();
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .cars(carDtos)
                .build();
    }
}
