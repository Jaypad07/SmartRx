package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.models.request.LoginRequest;
import com.sei.smartrx.models.response.LoginResponse;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.security.JWTUtils;
import com.sei.smartrx.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtils jwUtils;
    private AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager, @Lazy MyUserDetails myUserDetails ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    public User createUser(User userObject){
        if (!userRepository.existsByEmail(userObject.getEmail())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        }else throw new InformationExistException("User with email address " + userObject.getEmail() + " already exists");
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch(Exception e){
            return ResponseEntity.ok(new LoginResponse("Error: username or password is incorrect"));
        }
    }

    public User getUser(){
        Optional<User> user = userRepository.findById(getCurrentLoggedInUser().getId());
        if (user.isPresent()) {
            return user.get();
        } else throw new InformationNotFoundException("User with Id " + getCurrentLoggedInUser().getId() + " does not exist.");
    }

    public User updateUser(User userObject) throws InformationNotFoundException{
        User updatedUser = getUser();
        updatedUser.setFirstName(userObject.getFirstName());
        updatedUser.setLastName(userObject.getLastName());
        updatedUser.setEmail(getCurrentLoggedInUser().getEmail());
        updatedUser.setDob(userObject.getDob());
        updatedUser.setAllergies(userObject.getAllergies());
        updatedUser.setPassword(getCurrentLoggedInUser().getPassword());
        return userRepository.save(updatedUser);

    }

    public ResponseEntity<?> deleteUser() {
        User user = getUser();
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
