package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Command to update an existing Volunteer.
 * This class encapsulates the ID of the volunteer and the potentially updated data.
 * Fields are wrapped in Optional to indicate that they might not be present in the update request.
 */
public record UpdateVolunteerCommand(
        Long volunteerId,
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<String> dni,
        Optional<LocalDate> dateOfBirth,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<String> address
) {

    public UpdateVolunteerCommand {
        if (volunteerId == null) {
            throw new IllegalArgumentException("Volunteer ID cannot be null for UpdateVolunteerCommand");
        }
    }
}