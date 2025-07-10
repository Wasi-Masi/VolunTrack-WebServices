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
        @NotBlank(message = "{first.name.required}")
        String firstName,

        @NotBlank(message = "{last.name.required}")
        String lastName,

        @NotBlank(message = "{dni.required}")
        @Pattern(regexp = "^[0-9]{8}$", message = "{dni.invalid}")
        String dni,

        @NotNull(message = "{date.of.birth.required}")
        @Past(message = "{date.of.birth.past}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dateOfBirth,

        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email,

        @NotBlank(message = "{phone.required}")
        String phoneNumber,

        @NotBlank(message = "{address.required}")
        String address,

        @NotNull(message = "{organization.id.required}")
        Long organizationId,

        String profession
) {
}