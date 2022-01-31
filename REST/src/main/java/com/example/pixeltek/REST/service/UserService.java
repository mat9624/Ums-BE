package com.example.pixeltek.REST.service;

import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    Iterable<User> getAll();

    Optional<User> getById(String id);

    User create(User user);

    User update(User user, String token);

    Boolean delete(String id);

    User login(String email, String password);

}
