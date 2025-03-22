package com.dev.token.jwt.services.impl;

import com.dev.token.jwt.models.User;
import com.dev.token.jwt.repositories.UserRepository;
import com.dev.token.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User criarUser(User userModel) {
        return userRepository.save(userModel);
    }
}
