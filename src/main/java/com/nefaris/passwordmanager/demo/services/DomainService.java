package com.nefaris.passwordmanager.demo.services;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomainService {

    private final UserRepository userRepository;

    public DomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.findUserByUsername(username).ifPresent(user -> {
            user.getDomains().removeIf(d -> d.getDomainAddress().equals(domain) && d.getUsername().equals(domainUsername));
            userRepository.save(user);
        });
    }
}
