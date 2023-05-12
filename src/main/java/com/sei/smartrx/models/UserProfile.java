package com.sei.smartrx.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    @Column
    private String role;

    /**
     * One-To-One UserProfile to User. Every user gets a user profile that holds information like role, that gives user specific authorizations
     */
    @JsonIgnore
    @OneToOne(mappedBy = "userProfile")
    private User user;

    public UserProfile() {
    }

    public UserProfile(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userProfiles_id) {
        this.id = userProfiles_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userProfiles_id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
