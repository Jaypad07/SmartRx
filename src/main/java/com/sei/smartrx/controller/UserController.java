package com.sei.smartrx.controller;

import com.sei.smartrx.models.User;
import com.sei.smartrx.models.request.LoginRequest;
import com.sei.smartrx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping (path = "/auth/users/register")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }

    @PostMapping(path="/auth/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
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
