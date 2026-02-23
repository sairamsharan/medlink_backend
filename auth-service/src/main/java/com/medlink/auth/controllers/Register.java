package com.medlink.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medlink.auth.models.ErrorResponse;
import com.medlink.auth.models.UserModel;
import com.medlink.auth.services.UserService;

@RestController
public class Register {
    @Autowired
    private UserService uService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        try {
            String response = uService.register(user);
            ErrorResponse successResponse = new ErrorResponse(200, response);
            return ResponseEntity.ok().body(successResponse);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(500, "Registration failed. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
