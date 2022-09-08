package com.intuit.user.repository;

import com.intuit.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldReturnUserByAGivenEmail() {
        User expectedUser = new User(1L, "fname", "lname", "email@example.com", "password");
        userRepository.save(expectedUser);

        Optional<User> actualUserOptional = userRepository.findByEmail("email@example.com");
        assertTrue(actualUserOptional.isPresent());
        User actualUser = actualUserOptional.get();

        assertEquals("email@example.com", actualUser.getEmail());
        assertEquals("fname", actualUser.getFirstName());
        assertEquals("lname", actualUser.getLastName());
        assertEquals("password", actualUser.getPassword());
    }

    @Test
    void shouldReturnEmptyOptionalWhenUserDoesNotExistWithTheGivenEmail() {
        User expectedUser = new User(1L, "fname", "lname", "email@example.com", "password");
        userRepository.save(expectedUser);

        Optional<User> actualUserOptional = userRepository.findByEmail("different-email@example.com");
        assertFalse(actualUserOptional.isPresent());
    }
}