package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Query service for retrieving Organization-related data.
 * This service handles incoming queries and delegates data retrieval to the organization repository.
 */
@Service
public class OrganizationQueryService {

    private final IOrganizationRepository organizationRepository;

    /**
     * Constructs a new OrganizationQueryService.
     *
     * @param organizationRepository The repository for Organization entities.
     */
    public OrganizationQueryService(IOrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Handles the query to retrieve a specific organization by its ID.
     *
     * @param query The query containing the organization ID.
     * @return An Optional containing the Organization if found, otherwise empty.
     */
    public Optional<Organization> handle(GetOrganizationQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

}