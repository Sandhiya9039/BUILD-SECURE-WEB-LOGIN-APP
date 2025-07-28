package com.secureapp.geologinapp.service;

import com.secureapp.geologinapp.model.User;
import com.secureapp.geologinapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    public boolean emailExists(String email) {
        return userRepo.existsByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void enableUser(User user) {
        user.setEnabled(true);
        userRepo.save(user);
    }
}
