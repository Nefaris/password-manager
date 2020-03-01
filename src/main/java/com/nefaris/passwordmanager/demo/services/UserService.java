package com.nefaris.passwordmanager.demo.services;

import com.nefaris.passwordmanager.demo.models.RegisterFormData;
import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(RegisterFormData registerFormData) {
        boolean usernameExists = userRepository.findUserByUsername(registerFormData.getUsername()).isPresent();
        if (usernameExists) return false;

        boolean emailExists = userRepository.findUserByEmail(registerFormData.getEmail()).isPresent();
        if (emailExists) return false;

        User user = new User(registerFormData);
        user.setPassword(passwordEncoder.encode(registerFormData.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean isUserAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    public boolean userExistsByUsername(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}