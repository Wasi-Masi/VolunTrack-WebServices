package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands;

import java.time.LocalDate;

/**
 * Command to create a new Volunteer.
 * This class encapsulates all the necessary data to perform the creation operation.
 * Commands are immutable objects representing an intention to change the system's state.
 */
public record CreateVolunteerCommand(
        String firstName,
        String lastName,
        String dni,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        String address,
        Long organizationId,
        String profession
) {

}