package com.example.ums.service;

import com.example.ums.DTO.UserDTO;
import com.example.ums.UserRepository.UserRepository;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.ums.service.UmsException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("DB")
public class UserServiceDB implements UserServiceInterface {
    @Autowired
    private UserRepository userRepo;


    @Override
    public Iterable<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepo.findById(id);
    }
    @Override
    public List<User> getUser(String email, String password)throws UmsException{
        try{
            List<User> users=userRepo.findByEmail(email);
            UUID uuid=UUID.randomUUID();
            users.get(0).setToken(uuid.toString());
            return users;
        }catch (Exception e){
            throw new UmsException(HttpStatus.NOT_FOUND,"fottiti");
        }
    }

    @Override
    public User create(User user) {
        List<User> listUtents= userRepo.findByEmail(user.getEmail());
        if(!listUtents.isEmpty())throw new RuntimeException();
        else return userRepo.save(user);
    }

    @Override
    public Optional<User> update(User user) {
        Optional<User> foundUser = userRepo.findById(user.getId());

        if (foundUser.isEmpty()) {
            return Optional.empty();
        }

        foundUser.get().setName(user.getName());
        foundUser.get().setSurname(user.getSurname());
        foundUser.get().setEmail(user.getEmail());

        return foundUser;
    }

    @Override
    public Boolean delete(String email) {
        List<User> foundUser = userRepo.findByEmail(email);

        if (foundUser.isEmpty()) {
            return false;
        }
        userRepo.delete(foundUser.get(0));

        return true;
    }
    @Override
    public UserDTO convertToDTO(User u){
        UserDTO uDTO = new UserDTO();
        uDTO.setId(u.getId());
        uDTO.setEmail(u.getEmail());
        uDTO.setPassword(u.getPassword());
        uDTO.setName(u.getName());
        uDTO.setSurname(u.getSurname());
        return uDTO;
    }

}
