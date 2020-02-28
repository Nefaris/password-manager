package com.nefaris.passwordmanager.demo.services;

import com.nefaris.passwordmanager.demo.models.CustomUserDetails;
import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
        return user.map(CustomUserDetails::new).get();
    }
}