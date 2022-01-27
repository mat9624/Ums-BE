package com.example.ums.service.cache;

import com.example.ums.UserRepository.UserRepository;
import com.example.ums.service.cache.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CacheToken {

    @Autowired
    UserRepository userRepo;

    //FIXME le variabili che non vengono mai riassegnate dovrebbero essere chiarate come final
    private Map<User, String> tokens;

    public CacheToken() {
        tokens = new HashMap<>();
    }

    public void insert(User user, String token) {
        tokens.put(user, token);
    }

    public Boolean tokenFound(String token) {
        //FIXME il system.out.printl(...) andrebbe usato solo temporaneamtne in fase di sviluppo
        // a fini di debugging in quanto è bloccante, non sicuro e non thread-safe
        System.out.println(tokens);

        //return tokens.containsValue(token) ? true : !userRepo.findByToken(token).isEmpty();
        if (!tokens.containsValue(token)) {
            List<User> tokenFoundDB = userRepo.findByToken(token);
            return !tokenFoundDB.isEmpty();
        }
        return true;

    }


}
