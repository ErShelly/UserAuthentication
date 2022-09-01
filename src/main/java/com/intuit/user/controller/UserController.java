package com.intuit.user.controller;

import com.intuit.user.dto.SignUpRequestDTO;
import com.intuit.user.model.User;
import com.intuit.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        return new ResponseEntity<>(userService.find(email), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return new ResponseEntity<>(userService.create(signUpRequestDTO.getFirstName(),
                signUpRequestDTO.getLastName(),
                signUpRequestDTO.getEmail(),
                signUpRequestDTO.getPassword()), HttpStatus.OK);
    }
}
