package com.dev.token.jwt.services.impl;

import com.dev.token.jwt.models.User;
import com.dev.token.jwt.repositories.UserRepository;
import com.dev.token.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    UserRepository userRepository;

    @Override
    public User criarUser(User userModel) {
        String encode = passwordEncoder.encode(userModel.getPassword());

        userModel.setPassword(encode);
        return userRepository.save(userModel);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
