package com.sei.smartrx.controller;

import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import com.sei.smartrx.models.request.LoginRequest;
import com.sei.smartrx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST: endpoint http://localhost:8080/api/auth/users/register
     * @param userObject
     * @return model User
     */
    @PostMapping (path = "/auth/users/register")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }
    /**
     * POST: endpoint http://localhost:8080/api/auth/users/login
     * @param loginRequest object
     * @return ResponseEntity that allows developer to access status codes, headers, and response body.
     */
    @PostMapping(path="/auth/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }
    /**
     * GET: http://localhost:8080/api/users
     * @return model User
     */
    @GetMapping(path = "/users")
    public User getUser() {
        return userService.getUser();
    }
    /**
     * PUT: http://localhost:8080/api/users
     * @param Long userId, RequestBody UserObject
     * @return model User
     */
    @PutMapping(path = "/users")
    public User updateUser(@RequestBody User userObject) {
       return userService.updateUser(userObject);
    }
    /**
     * DELETE: http://localhost:8080/api/users
     * @param Long userId
     * @return ResponseEntity that allows developer to access status codes, headers, and response body.
     */
    @DeleteMapping(path = "/users")
    public ResponseEntity<?> deleteUser() {
        return userService.deleteUser();
    }

}
