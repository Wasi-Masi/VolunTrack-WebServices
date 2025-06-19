package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

public record CreateOrganizationResource(
        String name,
        String description,
        String email,
        String plan
) {
}