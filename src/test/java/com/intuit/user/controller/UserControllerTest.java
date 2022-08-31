package com.intuit.user.controller;

import com.intuit.user.model.User;
import com.intuit.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateUser() throws Exception {
        User userCreated = new User("Eren", "Thomas", "e@gmail.com", "password");
        userCreated.setId(1L);

        when(userService.create(anyString(),anyString(), anyString(), anyString())).thenReturn(userCreated);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstName", "Eren")
                        .param("lastName", "Thomas")
                        .param("email", "e@gmail.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Eren"))
                .andExpect(jsonPath("$.lastName").value("Thomas"))
                .andExpect(jsonPath("$.email").value("e@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"));

        verify(userService, times(1)).create(anyString(), anyString(), anyString(), anyString());

    }
}