package com.secureapp.geologinapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OTP {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;
    private LocalDateTime expiryTime;

    // Getters and setters
}
