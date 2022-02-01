package com.example.pixeltek.REST.service;

import com.example.pixeltek.DAO.repository.UserRepository;
import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.configuration.CacheToken;
import com.example.pixeltek.REST.exception.UmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepositoryI;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CacheToken cacheToken;


    @Override
    public Optional<User> getById(String id) {
        return userRepositoryI.findById(id);
    }

    @Override
    public User login(String email, String password) throws UmsException {
        try {
            Optional<User> user = userRepositoryI.findByEmail(email);
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            user.get().setToken(token);
            boolean isPasswordMatches = passwordEncoder.matches(password, user.get().getPassword());
            if (isPasswordMatches) {
                userRepositoryI.save(user.get());
                cacheToken.insert(user.get(), token);
                return user.get();
            } else throw new UmsException(HttpStatus.NOT_FOUND, "password errata");
        } catch (Exception e) {
            throw new UmsException(HttpStatus.NOT_FOUND, "Utente inesistente");
        }
    }

    @Override
    public User create(User user) {
        Optional<User> userTmp = userRepositoryI.findByEmail(user.getEmail());
        if (!userTmp.isEmpty()) throw new RuntimeException();
        else {
            String pss = passwordEncoder.encode(user.getPassword());
            user.setPassword(pss);
            return userRepositoryI.save(user);
        }
    }

    @Override
    public User update(User user, String token) {
        try {
            Optional<User> users=userRepositoryI.findByEmail(user.getEmail());
            User updatedUser=users.get();
            updatedUser.setName(user.getName());
            updatedUser.setSurname(user.getSurname());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setToken(token);
            cacheToken.update(updatedUser,token);
            userRepositoryI.deleteById(users.get().getId());
            userRepositoryI.save(users.get());
            return users.get();

        }catch (Exception e){
            throw new UmsException(HttpStatus.NOT_FOUND, "L'utente non esiste");
        }


    }

    @Override
    public Boolean delete(String email) {
        Optional<User> foundUser = userRepositoryI.findByEmail(email);
        if (foundUser.isEmpty()) {
            return false;
        }
        userRepositoryI.delete(foundUser.get());
        return true;
    }


    public Boolean checkAuth(String auth) {
        return cacheToken.tokenFound(auth);
    }

}
