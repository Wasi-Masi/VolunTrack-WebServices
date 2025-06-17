package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands;

/**
 * Command to delete an existing Volunteer.
 * This class encapsulates the ID of the volunteer to be deleted.
 */
public record DeleteVolunteerCommand(
        Long volunteerId
) {
}