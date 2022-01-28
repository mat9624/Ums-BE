package com.example.pixeltek.DAO.repository;

import com.example.pixeltek.DTO.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class UserRepository implements UserRepositoryI {
    @Override
    public abstract List<User> findByEmail(String email);

    @Override
    public abstract List<User> findByToken(String token);

}
