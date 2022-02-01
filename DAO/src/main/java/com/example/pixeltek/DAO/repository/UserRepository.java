package com.example.pixeltek.DAO.repository;

import com.example.pixeltek.DTO.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //@Query("{email :?0}")
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);

}
