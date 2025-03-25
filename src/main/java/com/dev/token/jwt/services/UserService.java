package com.dev.token.jwt.services;

import com.dev.token.jwt.models.User;

import java.util.List;

public interface UserService {
    User criarUser(User userModel);

    List<User> findAll();
}
