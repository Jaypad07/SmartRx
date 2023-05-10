package com.sei.smartrx.controller;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
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


}
