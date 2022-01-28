package com.example.ums.service;

import com.example.ums.DTO.UserDTO;
import com.example.ums.UserRepository.UserRepository;
import com.example.ums.service.cache.CacheToken;
import com.example.ums.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("DB")
public class UserServiceDB implements UserServiceInterface {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CacheToken cacheToken;


    @Override
    public Iterable<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> getUser(String email, String password) throws UmsException {
        try {

            List<User> users = userRepo.findByEmail(email);
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            users.get(0).setToken(token);
            boolean isPasswordMatches = passwordEncoder.matches(password, users.get(0).getPassword());
            if (isPasswordMatches) {
                userRepo.save(users.get(0));
                cacheToken.insert(users.get(0), token);

                return users;
            } else throw new UmsException(HttpStatus.NOT_FOUND, "password errata");
        } catch (Exception e) {
            throw new UmsException(HttpStatus.NOT_FOUND, "L'utente non esiste");
        }
    }

    @Override
    public User create(User user) {
        List<User> getUsers = userRepo.findByEmail(user.getEmail());
        if (!getUsers.isEmpty()) throw new RuntimeException();
        else {
            String pss = passwordEncoder.encode(user.getPassword());
            user.setPassword(pss);
            return userRepo.save(user);
        }
    }

    @Override
    public User update(User user, String token) {
        //FIXME l'entità del db non viene resa persistente
        try {
            List<User> users=userRepo.findByEmail(user.getEmail());
            User updatedUser=users.get(0);
            updatedUser.setName(user.getName());
            updatedUser.setSurname(user.getSurname());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setToken(token);
            cacheToken.update(updatedUser,token);
            userRepo.deleteById(users.get(0).getId());
            userRepo.insert(users.get(0));
            return users.get(0);

        }catch (Exception e){
            throw new UmsException(HttpStatus.NOT_FOUND, "L'utente non esiste");
        }


    }

    @Override
    public Boolean delete(String email) {
        List<User> foundUser = userRepo.findByEmail(email);
        if (foundUser.isEmpty()) {
            return false;

        }
        userRepo.delete(foundUser.get(0));
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
