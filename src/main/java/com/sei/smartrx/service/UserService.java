package com.sei.smartrx.service;

import com.sei.smartrx.exceptions.InformationExistException;
import com.sei.smartrx.exceptions.InformationNotFoundException;
import com.sei.smartrx.models.User;
import com.sei.smartrx.models.UserProfile;
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

    /**
     * Retrives the current logged-in user.
     *
     * @return the User instance representing the current logged-in user.
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    /**
     * Creates a new user.
     *
     * @param userObject the User object containing user details
     * @return the created User object
     * @throws InformationNotFoundException if a user with the provided email already exists.
     */
    public User registerUser(User userObject){
        if (!userRepository.existsByEmail(userObject.getEmail())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            if (userObject.getUserProfile() == null) {
                userObject.setUserProfile(new UserProfile("ROLE_USER")); // Adding a new User Profile should set automatically increase the ID, and we can use setRole to set user privileges.
            }
            return userRepository.save(userObject);
        }else throw new InformationExistException("User with email address " + userObject.getEmail() + " already exists");
    }

    /**
     * Finds a user by their email.
     * @param email the email of the user to find
     * @return the User object corresponding to the provided email
     */
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }


    /**
     * Logs in a user with the provided credentials
     * @param loginRequest the LoginRequest object containing login credentials
     * @return a ResponseEntity containing a login response
     */

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
    /**
     * Retrieves the current user.
     * @return the User object representing the current user
     * @throws InformationNotFoundException if the user with the current logged-in user's ID does not exist.
     */
    public User getCurrentUser(){
        Optional<User> user = userRepository.findById(getCurrentLoggedInUser().getId());
        if (user.isPresent()) {
            return user.get();
        } else throw new InformationNotFoundException("User with Id " + getCurrentLoggedInUser().getId() + " does not exist.");
    }

    /**
     * Updates a user's details.
     * @param userObject the User object containing updated user details
     * @return the updated User object
     * @throws InformationNotFoundException if the current user does not exist
     */
    public User updateCurrentUser(User userObject) throws InformationNotFoundException{
        User updatedUser = getCurrentUser();
        updatedUser.setFirstName(userObject.getFirstName());
        updatedUser.setLastName(userObject.getLastName());
        updatedUser.setEmail(getCurrentLoggedInUser().getEmail());
        updatedUser.setDob(userObject.getDob());
        updatedUser.setAllergies(userObject.getAllergies());
        updatedUser.setPassword(getCurrentLoggedInUser().getPassword());
        return userRepository.save(updatedUser);
    }

    /**
     * Deletes the current user.
     * @return a ResponseEntity indicating the success of the delete operation
     */
    public ResponseEntity<?> deleteCurrentUser() {
        User user = getCurrentUser();
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
