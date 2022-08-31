package com.intuit.user.service;

import com.intuit.user.model.User;
import com.intuit.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void shouldCreateUser(){
        User userToBeCreated = new User("Eren", "Thomas", "e@agmail.com", "password");
        User userCreated = new User("Eren", "Thomas", "e@agmail.com", "password");
        userCreated.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(userCreated);
        User user = userService.create("Eren", "Thomas", "e@agmail.com", "password");

        assertEquals(userCreated, user);
        verify(userRepository, times(1)).save(userToBeCreated);
    }
}