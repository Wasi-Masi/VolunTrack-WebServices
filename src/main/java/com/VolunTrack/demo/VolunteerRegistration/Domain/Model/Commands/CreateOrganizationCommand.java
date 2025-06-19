package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands;

public record CreateOrganizationCommand(
        String name,
        String description,
        String email,
        String plan
) {
}