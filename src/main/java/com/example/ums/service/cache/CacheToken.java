package com.example.ums.service.cache;

import com.example.ums.UserRepository.UserRepository;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheToken {

    @Autowired
    UserRepository userRepo;


    private final Map<User, String> tokens;

    public CacheToken() {
        tokens = new HashMap<>();
    }

    public void insert(User user, String token) {
        tokens.put(user, token);
    }

    public Boolean tokenFound(String token) {
        return tokens.containsValue(token) ? true : !userRepo.findByToken(token).isEmpty();

    }

    public void update(User user, String token){
        tokens.replace(user,token);

    }


}
