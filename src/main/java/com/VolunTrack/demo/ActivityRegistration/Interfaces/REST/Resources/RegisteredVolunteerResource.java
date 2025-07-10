package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RegisteredVolunteerResource {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;      // 👈 NUEVO
    private String profession;
    private LocalDate registrationDate;
    private String status; // del voluntario
    private Long inscriptionId;
    private LocalDate inscriptionDate;
    private String inscriptionStatus;

}
