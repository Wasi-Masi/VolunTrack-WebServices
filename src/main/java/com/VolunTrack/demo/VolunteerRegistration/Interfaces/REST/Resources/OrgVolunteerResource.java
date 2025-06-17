package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources;

/**
 * Represents the resource for an OrgVolunteer association.
 * This DTO is used to represent the association data (e.g., when associating a volunteer to an organization)
 * when returned via the REST API.
 */
public record OrgVolunteerResource(
        Long organizationId,
        Long volunteerId
) {
}