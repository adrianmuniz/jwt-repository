package com.dev.token.jwt.controllers;


import com.dev.token.jwt.dto.JwtDto;
import com.dev.token.jwt.dto.LoginDto;
import com.dev.token.jwt.dto.UserDto;
import com.dev.token.jwt.models.User;
import com.dev.token.jwt.security.JwtProvider;
import com.dev.token.jwt.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/criar")
    public ResponseEntity<Object> registrarUser(@RequestBody UserDto userDto){
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userService.criarUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUser(@Valid @RequestBody LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.gerarToken(authentication);
            return ResponseEntity.ok(new JwtDto(jwt));
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario ou senha incorretos!");
        }
    }

    @GetMapping("/usuarios")
    public List<User> listarUsuarios(){
        return userService.findAll();
    }
}
