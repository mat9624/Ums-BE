package com.example.pixeltek.REST.service;
import com.example.pixeltek.DAO.repository.UserRepository;
import com.example.pixeltek.DTO.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {
    @Mock
    private UserRepository iUserRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    BCryptPasswordEncoder pw = new BCryptPasswordEncoder();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByIdTest() {
        User user = new User();
        user.setEmail("forty");
        Mockito.when(iUserRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        assertNotNull(userService.getById("ciao"));
    }

    @Test
    void getUserTest(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setEmail("fabio@gmail.com");
        String password="Fabio";
        BCryptPasswordEncoder pw = new BCryptPasswordEncoder();
        String passwordCripted = pw.encode(password);
        user.setPassword(passwordCripted);
        user.setName("Fabio");
        user.setSurname("Longo");
        users.add(user);
        Mockito.when(iUserRepository.findByEmail(Mockito.any())).thenReturn(users);
        assertNotNull(userService.login(user.getEmail(),password));
        assertNotNull(null);
    }
}
