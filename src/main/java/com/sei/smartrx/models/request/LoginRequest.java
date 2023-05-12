package com.sei.smartrx.models.request;

/**
 * takes the user email and password and forms a login object to pass in the loginUser controller method.
 */
public class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
