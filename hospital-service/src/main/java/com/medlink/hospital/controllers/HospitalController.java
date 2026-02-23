package com.medlink.hospital.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medlink.hospital.models.HospitalModel;
import com.medlink.hospital.repository.HospitalRepository;

@RestController
public class HospitalController {
    @Autowired
    private HospitalRepository hRepository;

    @GetMapping("/hospitals/id/{id}")
    public ResponseEntity<?> getHospitalById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(hRepository.findById(id)
                    .orElseThrow(() -> new Exception("Hospital not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/hospitals/{location}")
    public ResponseEntity<?> getHospitals(@PathVariable String location) {
        try {
            return ResponseEntity.ok(hRepository.findAllByLocation(location));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch hospitals. Please try again later.");
        }
    }

    @PostMapping("/hospitals/upload")
    public ResponseEntity<?> postHospitals(@RequestBody List<HospitalModel> hospitalList) {
        try {
            return ResponseEntity.ok(hRepository.saveAll(hospitalList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload hospitals. Please try again later.");
        }
    }
}
