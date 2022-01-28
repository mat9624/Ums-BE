package com.example.pixeltek.REST.service;
import com.example.pixeltek.DAO.repository.IUserRepository;
import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.service.cache.CacheToken;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserServiceTest {

    @Mock
    private IUserRepository iUserRepository;
    @InjectMocks
    private UserServiceImpl userService;
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

    }





}
