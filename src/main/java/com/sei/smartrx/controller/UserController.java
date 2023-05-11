package com.sei.smartrx.controller;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping (path = "/register")
    public User createUser(@RequestBody User userObject) {
        if (!userRepository.existsByEmail(userObject.getEmail())) {
//            userObject.setPassword(userObject.getPassword());
            return userRepository.save(userObject);
        }else throw new InformationExistException("User with email address " + userObject.getEmail() + " already exists");
    }

    @GetMapping(path = "/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }else throw new InformationNotFoundException("User with Id " + userId + "does not exist.");
    }

    @PutMapping(path = "/users/{userId}")
    public User updateUserAllergy(@PathVariable Long userId, @RequestBody User userObject) {
        User updatedUser = getUser(userId);
        updatedUser.setAllergies(userObject.getAllergies());
        return userRepository.save(updatedUser);
    }

    @DeleteMapping(path = "users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
