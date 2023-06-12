package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;


    @Autowired
    public UserService (UserRepository repository) {
        this.repository = repository;
    }


    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    private static String SHA(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void create(User user) {
        user.setPassword(SHA(user.getPassword()));
        repository.save(user);
    }


    public User editUser(Long id, User user) {
        Optional<User> entity = repository.findById(id);
        if(entity.isPresent()){
            user.setId(entity.get().getId());
            create(user);
            return user;
        }else{
            return null;
        }
    }

    public int delete(Long id) {
        Optional<User> entity = repository.findById(id);
        if(entity.isPresent()){
            repository.deleteById(id);
            return 1;
        }else {
            return 0;
        }
    }
}