package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries;

/**
 * Query to retrieve a specific Organization by its identifier.
 * This class encapsulates the necessary data (Organization ID) to perform the query.
 */
public record GetOrganizationQuery(
        Long organizationId
) {
    public GetOrganizationQuery {
        if (organizationId == null) {
            throw new IllegalArgumentException("Organization ID cannot be null for GetOrganizationQuery");
        }
    }
}