package com.example.ums.controller;

import com.example.ums.service.UserServiceInterface;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
//@Produces(MediaType.APPLICATION_JSON)
@Consumes()
public class UserController {
    @Autowired
    @Qualifier("DB")
    private UserServiceInterface userService;

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

    @RequestMapping(value="/ums/delete/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable String id){

        boolean isDeleted=userService.delete(id);

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
