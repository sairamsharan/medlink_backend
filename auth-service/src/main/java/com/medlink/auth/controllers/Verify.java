package com.medlink.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medlink.auth.models.ErrorResponse;
import com.medlink.auth.models.LoginResponse;
import com.medlink.auth.services.UserService;

@RestController
public class Verify {
    @Autowired
    private UserService uService;

    @PutMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam String email,
            @RequestParam String otp) {
        try {
            String token = uService.verifyAccount(email, otp);
            if (token == null) {
                throw new Exception("Incorrect OTP. Please try again.");
            }
            LoginResponse responseModel = new LoginResponse(token, 200);
            return ResponseEntity.ok().body(responseModel);
        } catch (Exception ex) {
            String response = ex.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(500, response);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
