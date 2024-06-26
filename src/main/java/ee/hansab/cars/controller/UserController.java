package ee.hansab.cars.controller;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.dto.UserDto;
import ee.hansab.cars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        Optional<UserDto> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/cars")
    public List<CarDto> getCarsByUserId(@PathVariable Long userId) {
        return userService.getCarsByUserId(userId);
    }
}



