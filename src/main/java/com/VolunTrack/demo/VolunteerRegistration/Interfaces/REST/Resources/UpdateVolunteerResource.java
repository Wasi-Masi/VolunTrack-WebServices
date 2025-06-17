package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Represents the resource for updating an existing Volunteer.
 * This DTO is used to receive partial update data from REST API calls.
 * Fields are wrapped in Optional to indicate that they might not be present in the update request.
 */
public record UpdateVolunteerResource(
        Optional<String> firstName,
        Optional<String> lastName,
        @Pattern(regexp = "^[0-9]{8}$", message = "DNI must be 8 digits")
        Optional<String> dni,
        @PastOrPresent(message = "Date of birth cannot be in the future")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> dateOfBirth,
        @Email(message = "Email must be valid")
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<String> address
) {
}