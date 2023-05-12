package com.sei.smartrx.security;

import com.sei.smartrx.models.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails{
    /**
     * UserDetails is an interface that Spring Security uses for user authentication.
     * this class is important because it allows you to access the user's details and custom properties.
     * This class will be used to create a UserDetails object that will be used by Spring Security.
     */

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

}
