package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

import java.time.LocalDate; // Para dateOfBirth y registrationDate

/**
 * Represents the resource for a Volunteer.
 * This DTO is used to represent Volunteer data when returned via the REST API.
 */
public record VolunteerResource(
        Long id,
        String firstName,
        String lastName,
        String dni,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        LocalDate registrationDate,
        String status,
        String address,
        String profession
) {
}