package com.nefaris.passwordmanager.demo.repositories;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.models.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void beforeEach() {
        userRepository.deleteAll();
    }

    @AfterEach
    private void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    public void SaveUserTest() {
        userRepository.save(new User());
        userRepository.save(new User());
        assertEquals(2, userRepository.count());
    }

    @Test
    public void SaveUserDataTest() {
        User user = new User();
        user.setUsername("testusername");
        user.setPassword("testpassword");
        user.setEmail("example@email.com");

        List<Domain> domains = new ArrayList<>();
        domains.add(new Domain("sample.com", "sampleusername", "samplepassword"));
        domains.add(new Domain("example.com", "exampleusername", "examplepassword"));
        user.setDomains(domains);

        userRepository.save(user);

        User userFromDb = userRepository.findUserByUsername("testusername").orElse(null);
        assertNotNull(userFromDb);
        assertEquals(1, userRepository.count());
        assertEquals("testusername", userFromDb.getUsername());
        assertEquals("testpassword", userFromDb.getPassword());
        assertEquals("example@email.com", userFromDb.getEmail());
        assertEquals(domains, userFromDb.getDomains());
        assertEquals(domains.get(0), userFromDb.getDomains().get(0));
        assertEquals(domains.get(1), userFromDb.getDomains().get(1));
    }
}