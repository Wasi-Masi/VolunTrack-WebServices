package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.VolunteerStatus;

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
        Optional<String> dni,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Optional<LocalDate> dateOfBirth,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<String> address,
        Optional<String> profession,
        Optional<VolunteerStatus> status
) {
}