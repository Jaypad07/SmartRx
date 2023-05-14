package com.sei.smartrx.controller;

import com.sei.smartrx.models.User;
import com.sei.smartrx.models.request.LoginRequest;
import com.sei.smartrx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public User registerUser(@RequestBody User userObject) {
        return userService.registerUser(userObject);
    }
    /**
     * POST: endpoint http://localhost:8080/api/auth/users/login
     * @param Request Body loginRequest object
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
        return userService.getCurrentUser();
    }

    /**
     * PUT: http://localhost:8080/api/users
     * @param RequestBody UserObject
     * @return model User with updated changes
     */
    @PutMapping(path = "/users")
    public User updateCurrentUser(@RequestBody User userObject) {
       return userService.updateCurrentUser(userObject);
    }
    /**
     * DELETE: http://localhost:8080/api/users
     * @param Long userId
     * @return ResponseEntity that allows developer to access status codes, headers, and response body.
     */
    @DeleteMapping(path = "/users")
    public ResponseEntity<?> deleteCurrentUser() {
        return userService.deleteCurrentUser();
    }
}
