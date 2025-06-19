package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands;

public record UpdateOrganizationCommand(
        Long organizationId,
        String name,
        String description,
        String email,
        String plan
) {
}