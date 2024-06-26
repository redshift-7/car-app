package ee.hansab.cars.controller;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<CarDto> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long carId) {
        Optional<CarDto> car = carService.getCarById(carId);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

