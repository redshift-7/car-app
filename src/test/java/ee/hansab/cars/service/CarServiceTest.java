package ee.hansab.cars.service;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.model.Car;
import ee.hansab.cars.model.User;
import ee.hansab.cars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;

    @BeforeEach
    public void setUp() {
        User user1 = new User(1L, "Teet Järveküla", List.of());
        User user2 = new User(2L, "Pille Purk", List.of());

        car1 = new Car(1L, "Lada", "2101", "123ASD", user1);
        car2 = new Car(2L, "Kia", "Sorento", "XYZ789", user1);
        car3 = new Car(3L, "Skoda", "Octavia", "999GLF", user2);
        car4 = new Car(4L, "Kia", "Sorento", "555TFF", user2);

        user1.setCars(List.of(car1, car2));
        user2.setCars(List.of(car3, car4));
    }

    @Test
    void testGetCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2, car3, car4));

        List<CarDto> cars = carService.getCars();

        assertEquals(4, cars.size());

        // Assert for car1
        assertEquals("Lada", cars.get(0).getMake());
        assertEquals("2101", cars.get(0).getModel());
        assertEquals("123ASD", cars.get(0).getNumberplate());
        assertEquals("Teet Järveküla", cars.get(0).getUser());

        // Assert for car2
        assertEquals("Kia", cars.get(1).getMake());
        assertEquals("Sorento", cars.get(1).getModel());
        assertEquals("XYZ789", cars.get(1).getNumberplate());
        assertEquals("Teet Järveküla", cars.get(1).getUser());

        // Assert for car3
        assertEquals("Skoda", cars.get(2).getMake());
        assertEquals("Octavia", cars.get(2).getModel());
        assertEquals("999GLF", cars.get(2).getNumberplate());
        assertEquals("Pille Purk", cars.get(2).getUser());
    }

    @Test
    void testGetCarById() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));

        Optional<CarDto> result = carService.getCarById(1L);

        assertTrue(result.isPresent());
        assertEquals("Lada", result.get().getMake());
        assertEquals("2101", result.get().getModel());
        assertEquals("123ASD", result.get().getNumberplate());
        assertEquals("Teet Järveküla", result.get().getUser());
    }

    @Test
    void testGetCarByIdNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CarDto> result = carService.getCarById(1L);

        assertFalse(result.isPresent());
    }
}
