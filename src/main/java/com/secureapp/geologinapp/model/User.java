package com.secureapp.geologinapp.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role = "USER";
    private String allowedLocation;
    private boolean enabled;

    // Getters and setters
}
