package com.intuit.user.controller;

import com.intuit.user.dto.LoginRequestDTO;
import com.intuit.user.dto.SignUpRequestDTO;
import com.intuit.user.model.User;
import com.intuit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find")
    public ResponseEntity<User> getUser(@Valid @NotBlank @RequestParam(required = true) String email) {
        return new ResponseEntity<>(userService.find(email), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return new ResponseEntity<>(userService.create(signUpRequestDTO.getFirstName(),
                signUpRequestDTO.getLastName(),
                signUpRequestDTO.getEmail(),
                signUpRequestDTO.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> authenticate(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseEntity<>(userService.authenticate(loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()), HttpStatus.OK);
    }
}
