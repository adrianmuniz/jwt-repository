package com.dev.token.jwt.controllers;


import com.dev.token.jwt.dto.UserDto;
import com.dev.token.jwt.models.User;
import com.dev.token.jwt.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/criar")
    public ResponseEntity<Object> registrarUser(@RequestBody UserDto userDto){
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userService.criarUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
