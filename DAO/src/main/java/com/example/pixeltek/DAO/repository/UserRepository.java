package com.example.pixeltek.DAO.repository;

import com.example.pixeltek.DTO.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //@Query("{email :?0}")
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);

}
