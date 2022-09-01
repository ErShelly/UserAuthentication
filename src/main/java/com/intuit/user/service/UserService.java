package com.intuit.user.service;

import com.intuit.user.exception.InvalidCredentialsException;
import com.intuit.user.exception.UserAlreadyExistsException;
import com.intuit.user.exception.UserNotFoundException;
import com.intuit.user.model.User;
import com.intuit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String firstName, String lastName, String email, String password){
        Optional<User> userExists = userRepository.findByEmail(email);
        if(userExists.isPresent()) throw new UserAlreadyExistsException("User already exists with same email id.");

        User user = new User(firstName, lastName, email, password);
        return userRepository.save(user);
    }

    public User find(String email) throws UserNotFoundException{
        Optional<User> user =  userRepository.findByEmail(email);

        if(!user.isPresent()) throw new UserNotFoundException(String.format("No user exists with email : %s", email));
        return  user.get();
    }

    public User authenticate(String email, String password) throws InvalidCredentialsException{
        Optional<User> user =  userRepository.findByEmailAndPassword(email, password);

        if(!user.isPresent()) throw new InvalidCredentialsException("Invalid Credentials.");
        return  user.get();
    }
}
