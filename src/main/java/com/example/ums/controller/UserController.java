package com.example.ums.controller;

import com.example.ums.command.DeleteCommand;
import com.example.ums.command.LoginCommand;
import com.example.ums.command.RegisterCommand;
import com.example.ums.command.UpdateCommand;
import com.example.ums.service.UserServiceInterface;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Consumes()
public class UserController {
    @Autowired
    @Qualifier("DB")
    private UserServiceInterface userService;

    UserController() {
    }

    @RequestMapping("/ums")
    public Iterable<User> getAll() {

        return userService.getAll();

    }

    @RequestMapping("/ums/{id}")
    public User getById(@PathVariable String id) {
        Optional<User> foundUser = userService.getById(id);

        if (foundUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return foundUser.get();
    }

    @PostMapping("/ums/create")
    public User create(@RequestBody RegisterCommand registerCmd) {
        User user = new User(registerCmd.getNome(), registerCmd.getCognome(), registerCmd.getEmail(), registerCmd.getPassword());
        return userService.create(user);
    }

    @PostMapping("/ums/update")
    public User update(@RequestBody UpdateCommand updateCmd, HttpServletRequest request) {
        User user= new User(updateCmd.getNome(), updateCmd.getCognome(), updateCmd.getEmail(), updateCmd.getPassword());
        return userService.update(user, request.getHeader("Authorization"));
    }

    @PostMapping("/ums/delete")
    public void delete(@RequestBody DeleteCommand deleteCmd) {

        boolean isDeleted = userService.delete(deleteCmd.getEmail());
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("/ums/getUser")
    public List<User> getUser(@RequestBody LoginCommand loginCmd) {
        return userService.getUser(loginCmd.getEmail(), loginCmd.getPassword());
    }

}

