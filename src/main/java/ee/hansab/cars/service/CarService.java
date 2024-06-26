package ee.hansab.cars.service;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.mapper.CarMapper;
import ee.hansab.cars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<CarDto> getCars() {
        return carRepository.findAll().stream().map(CarMapper::toDto).toList();
    }

    public Optional<CarDto> getCarById(Long carId) {
        return carRepository.findById(carId).map(CarMapper::toDto);
    }
}