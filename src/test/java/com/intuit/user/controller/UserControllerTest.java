package com.intuit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.user.dto.LoginRequestDTO;
import com.intuit.user.dto.SignUpRequestDTO;
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

        SignUpRequestDTO userRequest = new SignUpRequestDTO();
        userRequest.setFirstName("Eren");
        userRequest.setLastName("Thomas");
        userRequest.setEmail("e@gmail.com");
        userRequest.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Eren"))
                .andExpect(jsonPath("$.lastName").value("Thomas"))
                .andExpect(jsonPath("$.email").value("e@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"));

        verify(userService, times(1)).create(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        User user = new User("Eren", "Thomas", "e@gmail.com", "password");
        user.setId(1L);

        when(userService.authenticate(anyString(), anyString())).thenReturn(user);

        LoginRequestDTO userRequest = new LoginRequestDTO();
        userRequest.setEmail("e@gmail.com");
        userRequest.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Eren"))
                .andExpect(jsonPath("$.lastName").value("Thomas"))
                .andExpect(jsonPath("$.email").value("e@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"));

        verify(userService, times(1)).authenticate(anyString(), anyString());
    }

    @Test
    void shouldFindUserByEmail() throws Exception {
        User user = new User(1L, "Eren", "Thomas", "e@gmail.com", "password");

        when(userService.find(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/users/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "e@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Eren"))
                .andExpect(jsonPath("$.lastName").value("Thomas"))
                .andExpect(jsonPath("$.email").value("e@gmail.com"))
                .andExpect(jsonPath("$.password").value("password"));

        verify(userService, times(1)).find(anyString());
    }
}