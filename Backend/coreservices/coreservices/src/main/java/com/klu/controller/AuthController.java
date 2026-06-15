package com.klu.controller;

import com.klu.models.User;

import com.klu.repository.UserRepository;

import com.klu.security.JwtUtil;
import com.klu.service.MongoLogService;

import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MongoLogService mongoLogService;

    // SIGNUP
    @PostMapping("/signup")
    public Map<String, String> signup(
            @RequestBody User user,
            HttpServletRequest request
    ) {

        Map<String, String> response =
                new HashMap<>();

        User existingUser =
                userRepository.findByUsername(
                        user.getUsername()
                );

        if (existingUser != null) {

            response.put(
                    "message",
                    "Username Already Exists"
            );

            return response;
        }

        userRepository.save(user);

        mongoLogService.logUserActivity(
                user.getUsername(),
                "SIGNUP",
                request.getRemoteAddr()
        );

        response.put(
                "message",
                "Signup Successful"
        );

        return response;
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody Map<String, String> request,
            HttpServletRequest httpServletRequest
    ) {

        String username =
                request.get("username");

        String password =
                request.get("password");

        User user =
                userRepository.findByUsername(
                        username
                );

        if (
                user != null
                &&
                user.getPassword().equals(
                        password
                )
        ) {

            String token =
                    jwtUtil.generateToken(
                            username,
                            user.getRole() != null
                                    ? user.getRole().toUpperCase()
                                    : "USER"
                    );

            Map<String, String> response =
                    new HashMap<>();

            response.put(
                    "token",
                    token
            );

            response.put(
                    "role",
                    user.getRole()
            );

            response.put(
                    "message",
                    "Login Successful"
            );

            mongoLogService.logLogin(
                    username,
                    "SUCCESS"
            );

            mongoLogService.logUserActivity(
                    username,
                    "LOGIN_SUCCESS",
                    httpServletRequest.getRemoteAddr()
            );

            return response;
        }

        mongoLogService.logLogin(
                username,
                "FAILED"
        );

        mongoLogService.logUserActivity(
                username,
                "LOGIN_FAILED",
                httpServletRequest.getRemoteAddr()
        );

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid Credentials"
        );
    }
}
