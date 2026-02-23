package com.medlink.appointment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medlink.appointment.models.ContactModel;
import com.medlink.appointment.repository.ContactRepository;

@RestController
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    public ResponseEntity<?> contact(@RequestBody ContactModel contactModel) {
        try {
            ContactModel savedContact = contactRepository.save(contactModel);
            return ResponseEntity.ok(savedContact);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to submit contact information. Please try again later.");
        }
    }
}
