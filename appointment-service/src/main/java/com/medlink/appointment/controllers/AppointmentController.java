package com.medlink.appointment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medlink.appointment.models.PatientInfo;
import com.medlink.appointment.repository.PatientInfoRepository;
import com.medlink.appointment.utils.EmailUtil;

@RestController
public class AppointmentController {
    @Autowired
    private PatientInfoRepository pRepository;

    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("/appointment")
    public ResponseEntity<?> postAppointment(@RequestBody PatientInfo patientInfo) {
        try {
            PatientInfo savedPatientInfo = pRepository.save(patientInfo);
            emailUtil.sendAppointmentConfirmationEmail(
                patientInfo.getEmail(), patientInfo.getFullName(),
                patientInfo.getDate(), patientInfo.getTime());
            return ResponseEntity.ok(savedPatientInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create appointment. Please try again later.");
        }
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable long id) {
        try {
            List<PatientInfo> patientInfoList = pRepository.findAllByPatientId(id);
            return ResponseEntity.ok(patientInfoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch appointment information. Please try again later.");
        }
    }
}
