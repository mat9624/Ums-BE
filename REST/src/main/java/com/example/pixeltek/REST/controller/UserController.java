package com.example.pixeltek.REST.controller;

import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Consumes()
public class UserController {
    @Autowired
    private UserService userService;

    UserController() {
    }


    @RequestMapping("/ums")
    public Iterable<User> getAll(){

        return userService.getAll();

    }

    @RequestMapping("/ums/{id}")
    public User getById(@PathVariable String id){
        Optional<User> foundUser= userService.getById(id);

        if(foundUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return foundUser.get();
    }

    @RequestMapping(value="/ums/create", method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user){
        return userService.create(user);
    }

    @RequestMapping(value="/ums/update/{id})", method= RequestMethod.PUT)
    public User update(@Valid @PathVariable String id,@RequestBody User user){

        Optional<User> updatedUser= userService.update(user);

        if(updatedUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");

        }
        return updatedUser.get();
    }

    @RequestMapping(value="/ums/delete/{email}", method=RequestMethod.DELETE)
    public void delete(@PathVariable String email){

        boolean isDeleted=userService.delete(email);

        if(!isDeleted){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
    }

    @GetMapping("/ums/getUser/{cred}")
    public List<User> getUser(@PathVariable String cred){
        String[] creds= cred.split("-");
        String email=creds[0];
        String password=creds[1];

        return userService.getUser(email,password);
    }

}

