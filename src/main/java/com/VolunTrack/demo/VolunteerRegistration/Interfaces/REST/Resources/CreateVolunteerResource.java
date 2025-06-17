package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Represents the resource for creating a new Volunteer.
 * This DTO is used to receive input data from REST API calls.
 */
public record CreateVolunteerResource(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "DNI is required")
        @Pattern(regexp = "^[0-9]{8}$", message = "DNI must be 8 digits")
        String dni,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dateOfBirth,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Organization ID is required")
        Long organizationId,

        String profession
) {
}