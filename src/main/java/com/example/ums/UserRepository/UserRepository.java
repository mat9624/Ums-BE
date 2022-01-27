package com.example.ums.UserRepository;

import com.example.ums.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByEmail(String email);
    List<User> findByToken(String token);

}
