package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
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
     * (Este método puede permanecer si hay otros usos internos o si decides en el futuro tener más de una organización)
     *
     * @param query The query containing the organization ID.
     * @return An Optional containing the Organization if found, otherwise empty.
     */
    public Optional<Organization> handle(GetOrganizationQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    /**
     * Retrieves the single organization available in the system.
     * This method assumes there should only be one organization stored.
     * It fetches all organizations and returns the first one found, if any.
     *
     * @return An Optional containing the single Organization if found, or empty otherwise.
     */
    public Optional<Organization> getTheSingleOrganization() {
        List<Organization> organizations = organizationRepository.findAll();
        if (organizations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(organizations.get(0));
    }
}