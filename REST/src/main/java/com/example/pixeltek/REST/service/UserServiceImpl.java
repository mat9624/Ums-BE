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
    public Iterable<User> getAll() {
        return userRepositoryI.findAll();
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepositoryI.findById(id);
    }

    @Override
    public User login(String email, String password) throws UmsException {
        try {
            List<User> users = userRepositoryI.findByEmail(email);
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            users.get(0).setToken(token);
            boolean isPasswordMatches = passwordEncoder.matches(password, users.get(0).getPassword());
            if (isPasswordMatches) {
                userRepositoryI.save(users.get(0));
                cacheToken.insert(users.get(0), token);
                return users.get(0);
            } else throw new UmsException(HttpStatus.NOT_FOUND, "password errata");
        } catch (Exception e) {
            throw new UmsException(HttpStatus.NOT_FOUND, "fottiti");
        }
    }

    @Override
    public User create(User user) {
        List<User> listUtents = userRepositoryI.findByEmail(user.getEmail());
        if (!listUtents.isEmpty()) throw new RuntimeException();
        else {
            String pss = passwordEncoder.encode(user.getPassword());
            user.setPassword(pss);
            return userRepositoryI.save(user);
        }
    }

    @Override
    public User update(User user, String token) {
        try {
            List<User> users=userRepositoryI.findByEmail(user.getEmail());
            User updatedUser=users.get(0);
            updatedUser.setName(user.getName());
            updatedUser.setSurname(user.getSurname());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setToken(token);
            cacheToken.update(updatedUser,token);
            userRepositoryI.deleteById(users.get(0).getId());
            userRepositoryI.insert(users.get(0));
            return users.get(0);

        }catch (Exception e){
            throw new UmsException(HttpStatus.NOT_FOUND, "L'utente non esiste");
        }


    }

    @Override
    public Boolean delete(String email) {
        List<User> foundUser = userRepositoryI.findByEmail(email);
        if (foundUser.isEmpty()) {
            return false;
        }
        userRepositoryI.delete(foundUser.get(0));
        return true;
    }


    public Boolean checkAuth(String auth) {
        return cacheToken.tokenFound(auth);
    }

}
