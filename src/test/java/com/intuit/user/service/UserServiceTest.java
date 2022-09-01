package com.intuit.user.service;

import com.intuit.user.model.User;
import com.intuit.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @MockBean
    UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserService userService;

    @Test
    void shouldCreateUser(){
        User userCreated = new User("Eren", "Thomas", "e@agmail.com", "encoded-password");
        userCreated.setId(1L);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(userCreated);

        User actualUser = userService.create("Eren", "Thomas", "e@agmail.com", "password");

        assertEquals(userCreated, actualUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFindUserByEmail(){
        User user = new User(1L,"Eren", "Thomas", "e@agmail.com", "password");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User actualUser = userService.find("e@gmail.com");
        assertEquals(user, actualUser);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}