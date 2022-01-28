package com.example.pixeltek.REST.service;

import com.example.pixeltek.DAO.repository.IUserRepository;
import com.example.pixeltek.DTO.dto.UserDTO;
import com.example.pixeltek.DTO.model.User;
import com.example.pixeltek.REST.service.cache.CacheToken;
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
    private IUserRepository userRepositoryI;
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
    public List<User> getUser(String email, String password) throws UmsException {
        try {

            List<User> users = userRepositoryI.findByEmail(email);
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            users.get(0).setToken(token);
            boolean isPasswordMatches = passwordEncoder.matches(password, users.get(0).getPassword());
            if (isPasswordMatches) {
                userRepositoryI.save(users.get(0));
                cacheToken.insert(users.get(0), token);

                return users;
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
    public Optional<User> update(User user) {
        Optional<User> foundUser = userRepositoryI.findById(user.getId());

        if (foundUser.isEmpty()) {
            return Optional.empty();
        }

        foundUser.get().setName(user.getName());
        foundUser.get().setSurname(user.getSurname());
        foundUser.get().setEmail(user.getEmail());

        return foundUser;
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

    @Override
    public UserDTO convertToDTO(User u) {
        UserDTO uDTO = new UserDTO();
        uDTO.setId(u.getId());
        uDTO.setEmail(u.getEmail());
        uDTO.setPassword(u.getPassword());
        uDTO.setName(u.getName());
        uDTO.setSurname(u.getSurname());
        return uDTO;
    }

    public Boolean checkAuth(String auth) {
        return cacheToken.tokenFound(auth);
    }


}
