package com.example.pixeltek.REST.controller;

import com.example.pixeltek.DTO.command.DeleteCommand;
import com.example.pixeltek.DTO.command.LoginCommand;
import com.example.pixeltek.DTO.command.RegisterCommand;
import com.example.pixeltek.DTO.command.UpdateCommand;
import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    UserController() {
    }

    @PostMapping("/ums/create")
    public UserDTO create(@RequestBody RegisterCommand registerCmd) {
        User user = new User(registerCmd.getName(), registerCmd.getSurname(), registerCmd.getEmail(), registerCmd.getPassword());
        return toDTO(userService.create(user));
    }

    @PostMapping("/ums/update")
    public UserDTO update(@RequestBody UpdateCommand updateCmd, HttpServletRequest request) {
        User user= new User(updateCmd.getName(), updateCmd.getSurname(), updateCmd.getEmail(), updateCmd.getPassword());
        return toDTO(userService.update(user, request.getHeader("Authorization")));
    }

    @PostMapping("/ums/delete")
    public void delete(@RequestBody DeleteCommand deleteCmd) {

        boolean isDeleted = userService.delete(deleteCmd.getEmail());
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("/ums/getUser")
    public UserDTO getUser(@RequestBody LoginCommand loginCmd) {
        return toDTO(userService.login(loginCmd.getEmail(), loginCmd.getPassword()));
    }

    private UserDTO toDTO(User user){
        UserDTO userDTO= modelMapper.map(user,UserDTO.class);
        return userDTO;
    }
}

