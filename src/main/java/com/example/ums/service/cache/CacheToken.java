package com.example.ums.service.cache;

import com.example.ums.UserRepository.UserRepository;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheToken {
    @Autowired
    UserRepository userRepo;
    private Map<User, String> tokens;

    public CacheToken(){
        tokens=new HashMap<>();
    }

    public void insert(User users, String token){
        tokens.put(users,token);
    }

    public Boolean tokenFound(String token){
        if(!tokens.containsValue(token)){
            List<User> tokenFoundDB=userRepo.findByToken(token);
            return !tokenFoundDB.isEmpty();
        }
        return true;
    }



}
