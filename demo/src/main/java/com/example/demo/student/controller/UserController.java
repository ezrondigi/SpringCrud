package com.example.demo.student.controller;

import com.example.demo.student.service.UserService;
import com.example.demo.student.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService service;


    public UserController(UserService userService){
        this.service = userService;
    }

    //Edits User
    @PutMapping(path = "{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        User newUser = service.editUser(id,user);
        if(newUser!=null){
            return new ResponseEntity<>(service.getUser(id), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Returns all users
    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    //Creates Users
    @PostMapping
    public String createUser(@RequestBody User user) {
        service.create(user);
        return "User created";
    }

    //Deletes user
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<User> delete(@PathVariable("userId") Long id){
        int status = service.delete(id);
        if(status == 1){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
