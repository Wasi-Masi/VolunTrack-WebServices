package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

public record UpdateOrganizationResource(
        String name,
        String description,
        String email,
        String plan
) {
}