package com.nefaris.passwordmanager.demo.services;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.models.RegisterFormData;
import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExistsByUsername(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
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

    public List<Domain> getDomains(String username) {
        User user = userRepository.findUserByUsername(username).orElse(null);
        return user != null ? user.getDomains() : new ArrayList<>();
    }

    public void addDomain(String username, Domain domain) {
        userRepository.findUserByUsername(username).ifPresent(user -> {
            user.getDomains().add(domain);
            userRepository.save(user);
        });
    }

    public void removeDomain(String username, String domain, String domainUsername) {
        System.out.println(username);

        userRepository.findUserByUsername(username).ifPresent(user -> {
            user.getDomains().removeIf(d -> d.getDomainAddress().equals(domain) && d.getUsername().equals(domainUsername));
            userRepository.save(user);
        });
    }
}