package com.example.pixeltek.REST.controller;

import ch.qos.logback.classic.Logger;
import com.example.pixeltek.DTO.command.DeleteCommand;
import com.example.pixeltek.DTO.command.LoginCommand;
import com.example.pixeltek.DTO.command.RegisterCommand;
import com.example.pixeltek.DTO.command.UpdateCommand;
import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;

@RestController
@CrossOrigin(origins = "*")
@Consumes()
@Slf4j
public class UserController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;



    UserController() {
    }

    @PostMapping("/ums/create")
    public UserDTO create(@RequestBody RegisterCommand registerCmd) {
        log.info("{}-{}-{} ",UserController.class,"/create",registerCmd.getName());
        log.debug("{}-{}-{} ",UserController.class,"/create",registerCmd.getName());
        User user = new User(registerCmd.getName(), registerCmd.getSurname(), registerCmd.getEmail(), registerCmd.getPassword());
        log.info("{}-{}-{} ",UserController.class,"/create",registerCmd.getName());
        return toDTO(userService.create(user));
    }

    @PostMapping("/ums/update")
    public UserDTO update(@RequestBody UpdateCommand updateCmd, HttpServletRequest request) {
        log.info("{}-{}-{} ",UserController.class,"/update",updateCmd.getName());
        log.debug("{}-{}-{} ",UserController.class,"/update",updateCmd.getName());
        User user= new User(updateCmd.getName(), updateCmd.getSurname(), updateCmd.getEmail(), updateCmd.getPassword());
        log.info("{}-{}-{} ",UserController.class,"/update",updateCmd.getName());
        return toDTO(userService.update(user, request.getHeader("Authorization")));
    }

    @PostMapping("/ums/delete")
    public void delete(@RequestBody DeleteCommand deleteCmd) {
        log.info("{}-{}-{} ",UserController.class,"/delete",deleteCmd.toString());
        log.debug("{}-{}-{} ",UserController.class,"/delete",deleteCmd.toString());
        boolean isDeleted = userService.delete(deleteCmd.getEmail());
        log.info("{}-{}-{} ",UserController.class,"/delete",deleteCmd.toString());
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("/ums/getUser")
    public UserDTO getUser(@RequestBody LoginCommand loginCmd) {
        log.info("{}-{}-{} ",UserController.class,"/getUser",loginCmd.toString());
        log.debug("{}-{}-{} ",UserController.class,"/getUser",loginCmd.toString());
        return toDTO(userService.login(loginCmd.getEmail(), loginCmd.getPassword()));
    }

    private UserDTO toDTO(User user){
        UserDTO userDTO= modelMapper.map(user,UserDTO.class);
        return userDTO;
    }
}

