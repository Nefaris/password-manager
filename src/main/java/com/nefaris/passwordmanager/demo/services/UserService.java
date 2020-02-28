package com.nefaris.passwordmanager.demo.services;

import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExistsByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElse(null);
        return user != null;
    }

    public boolean addNewUser(User user) {
        if (userExistsByUsername(user.getUsername())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return true;
    }
}