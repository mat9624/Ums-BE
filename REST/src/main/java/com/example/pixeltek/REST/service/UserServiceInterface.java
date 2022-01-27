package com.example.pixeltek.REST.service;

import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;

import java.util.List;
import java.util.Optional;


public interface UserServiceInterface {


    Iterable<User> getAll();

    Optional<User> getById(String id);

    User create(User user);

    Optional<User> update(User user);

    Boolean delete(String id);

    List<User> getUser(String email, String password);

    UserDTO convertToDTO(User u);
}
