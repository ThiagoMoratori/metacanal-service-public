package br.com.metacanal.service;

import br.com.metacanal.entity.User;
import br.com.metacanal.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String user = "{" +
                "\"name\": \"a name\"," +
                "\"email\": \"an email\"" +
                "}";

        userService.create(objectMapper.readTree(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void getUsers() {
        userService.getUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getByName() {
        userService.getByName("a name");
        verify(userRepository, times(1)).findByName(anyString());
    }

    @Test
    void getById() {
        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        Optional<User> userReturned = userService.getById(1L);

        assertTrue(userReturned.isPresent());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setId(2L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        Long idToDelete = 2L;
        userService.deleteUser(idToDelete);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateName() {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("name", "Goo ruh");

        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        userService.updateName(nameMap, 1L);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any());
    }
}