package com.intuit.user.service;

import com.intuit.user.exception.InvalidCredentialsException;
import com.intuit.user.exception.UserAlreadyExistsException;
import com.intuit.user.exception.UserNotFoundException;
import com.intuit.user.model.User;
import com.intuit.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.*;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User create(String firstName, String lastName, String email, String password){
        Optional<User> userExists = userRepository.findByEmail(email);
        if(userExists.isPresent()) throw new UserAlreadyExistsException("User already exists with same email id.");

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User(firstName, lastName, email, encodedPassword);
        return userRepository.save(user);
    }

    public User find(String email) throws UserNotFoundException{
        Optional<User> user =  userRepository.findByEmail(email);

        if(!user.isPresent()) throw new UserNotFoundException(String.format("No user exists with email : %s", email));
        return  user.get();
    }

    public User authenticate(String email, String password) throws InvalidCredentialsException{
        Optional<User> user =  userRepository.findByEmail(email);

        if(!user.isPresent()) throw new UserNotFoundException(String.format("No user exists with email : %s", email));

        boolean passwordMatches = bCryptPasswordEncoder.matches(password, user.get().getPassword());
        if(!passwordMatches) throw new InvalidCredentialsException("Invalid Credentials.");

        return  user.get();
    }
}
