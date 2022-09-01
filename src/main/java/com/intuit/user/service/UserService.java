package com.intuit.user.service;

import com.intuit.user.exception.UserNotFoundException;
import com.intuit.user.model.User;
import com.intuit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String firstName, String lastName, String email, String password){
        User user = new User(firstName, lastName, email, password);
        return userRepository.save(user);
    }

    public User find(String email) throws UserNotFoundException{
        User user =  userRepository.findByEmail(email);

        if(user == null) throw new UserNotFoundException(String.format("No user exists with email : %s", email));
        return  user;
    }
}
