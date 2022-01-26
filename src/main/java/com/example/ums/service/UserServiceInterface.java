package com.example.ums.service;

import com.example.ums.DTO.UserDTO;
import com.example.ums.model.User;
import org.springframework.web.bind.annotation.RequestHeader;

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
