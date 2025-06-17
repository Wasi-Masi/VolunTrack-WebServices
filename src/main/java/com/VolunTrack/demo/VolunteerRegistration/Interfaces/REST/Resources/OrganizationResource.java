package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

/**
 * Represents the resource for an Organization.
 * This DTO is used to represent Organization data when returned via the REST API.
 */
public record OrganizationResource(
        Long id,
        String name,
        String description,
        String email,
        String plan
) {
}