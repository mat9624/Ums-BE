package com.example.pixeltek.REST.configuration;

import com.example.pixeltek.DAO.repository.UserRepository;
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

    private final Map<User, String> tokens;

    public CacheToken() {
        tokens = new HashMap<>();
    }

    public void insert(User user, String token) {
        tokens.put(user, token);
    }

    public Boolean tokenFound(String token) {
        if (!tokens.containsValue(token)) {
            List<User> tokenFoundDB = userRepo.findByToken(token);
            return !tokenFoundDB.isEmpty();
        }
        return true;
    }

    public void update(User user, String token) {
        tokens.replace(user, token);

    }
}
