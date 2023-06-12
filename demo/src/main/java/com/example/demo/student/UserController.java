package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService service;


    public UserController(UserService userService){
        this.service = userService;
    }

    @PutMapping(path = "{id}")
    public String editUser(@PathVariable Long id,@RequestBody User user) {
        User newUser = service.editUser(id,user);
        if(newUser!=null){
            return "User Updated";
        }else {
            return "User not found";
        }
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        service.create(user);
        return "User created";
    }

    @DeleteMapping(path = "{userId}")
    public String delete(@PathVariable("userId") Long id){
        int status = service.delete(id);
        if(status == 1){
            return "User deleted";
        }else {
            return "User not found";
        }
    }
}
