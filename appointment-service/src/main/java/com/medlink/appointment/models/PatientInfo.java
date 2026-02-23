package com.medlink.appointment.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PatientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long patientId;

    @NotBlank(message = "Name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotNull(message = "Appointment date is required")
    private String date;

    @NotBlank(message = "Appointment time is required")
    private String time;

    @NotBlank(message = "Additional information is required")
    private String message;
}
