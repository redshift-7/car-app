package ee.hansab.cars.service;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.dto.UserDto;
import ee.hansab.cars.mapper.CarMapper;
import ee.hansab.cars.mapper.UserMapper;
import ee.hansab.cars.model.User;
import ee.hansab.cars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(UserMapper::toDto);
    }

    public List<CarDto> getCarsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getCars)
                .orElse(Collections.emptyList())
                .stream()
                .map(CarMapper::toDto)
                .toList();
    }
}
