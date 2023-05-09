package com.sei.smartrx.models;

import javax.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Long userProfile_id;
    @Column
    private String role;

}
