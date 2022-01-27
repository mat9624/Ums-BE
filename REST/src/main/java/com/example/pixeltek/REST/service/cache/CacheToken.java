package com.example.pixeltek.REST.service.cache;

import com.example.pixeltek.DAO.userRepository.UserRepository;
import com.example.pixeltek.DTO.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheToken {
    @Autowired
    UserRepository userRepo;
    private Map<User, String> tokens;

    public CacheToken() {
        tokens = new HashMap<>();
    }

    public void insert(User user, String token) {
        tokens.put(user, token);
    }

    public Boolean tokenFound(String token) {
        System.out.println(tokens);
        if (!tokens.containsValue(token)) {
            List<User> tokenFoundDB = userRepo.findByToken(token);
            return !tokenFoundDB.isEmpty();
        }
        return true;
    }


}
