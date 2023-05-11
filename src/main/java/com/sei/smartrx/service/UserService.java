package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.repository.UserRepository;
import com.sei.smartrx.security.JWTUtils;
import com.sei.smartrx.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    public User createUser(User userObject){
        if (!userRepository.existsByEmail(userObject.getEmail())) {
//            userObject.setPassword(userObject.getPassword());
            return userRepository.save(userObject);
        }else throw new InformationExistException("User with email address " + userObject.getEmail() + " already exists");
    }

    public User getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else throw new InformationNotFoundException("User with Id " + userId + " does not exist.");
    }

    public boolean getUserByEmail(String email){
        if(userRepository.existsByEmail(email)){
            return false;
        }
        return true;
    }

    public User updateUser(Long userId, User userObject) throws InformationNotFoundException{
        User updatedUser = getUser(userId);
        updatedUser.setFirstName(userObject.getFirstName());
        updatedUser.setLastName(userObject.getLastName());
        updatedUser.setEmail(userObject.getEmail());
        updatedUser.setDob(userObject.getDob());
        updatedUser.setAllergies(userObject.getAllergies());
        updatedUser.setPassword(userObject.getPassword());
        return userRepository.save(updatedUser);

    }

    public ResponseEntity<?> deleteUser(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmail(email);
    }

}
