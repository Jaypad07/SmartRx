package com.sei.smartrx.controller;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping (path = "/users/register")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }

    @PostMapping(path="/users/login")
    public User loginUser(@RequestBody User userObject){
        return userService.findUserByEmail(userObject.getEmail());
    }

    @GetMapping(path = "/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping(path = "/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User userObject) {
       return userService.updateUser(userId, userObject);
    }

    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

}
