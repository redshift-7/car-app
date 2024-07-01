package ee.hansab.cars.controller;

import ee.hansab.cars.dto.CarDto;
import ee.hansab.cars.dto.UserDto;
import ee.hansab.cars.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserById_invalidFormat() throws Exception {
        String invalidUserId = "abc";

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUsers() throws Exception {
        List<UserDto> users = Arrays.asList(
                UserDto.builder().id(1L).name("John").build(),
                UserDto.builder().id(2L).name("Jane").build()
        );

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(userService, times(1)).getUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getUserById() throws Exception {
        Long userId = 1L;
        UserDto userDto = UserDto.builder().id(userId).name("John").build();

        when(userService.getUserById(userId)).thenReturn(Optional.of(userDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));

        verify(userService, times(1)).getUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getUserById_userNotFound() throws Exception {
        Long userId = 999L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(userId);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void getCarsByUserId() throws Exception {
        Long userId = 1L;
        List<CarDto> cars = Arrays.asList(
                CarDto.builder().id(1L).make("Toyota").build(),
                CarDto.builder().id(2L).make("Honda").build()
        );

        when(userService.getCarsByUserId(userId)).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/cars", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].make").value("Honda"));

        verify(userService, times(1)).getCarsByUserId(userId);
        verifyNoMoreInteractions(userService);
    }
}