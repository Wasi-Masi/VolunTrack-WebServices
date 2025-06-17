package com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories;

import com.VolunTrack.demo.Shared.Infrastructure.Persistence.JPA.Repositories.BaseRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Organization} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 * This interface can also define custom queries specific to Organization operations.
 */
@Repository
public interface IOrganizationRepository extends BaseRepository<Organization, Long> {
    /**
     * Finds an organization by its email address.
     * This is an example of a custom query method not present in the generic BaseRepository.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param email The email address of the organization.
     * @return An Optional containing the Organization if found, or empty otherwise.
     */
    Optional<Organization> findByEmail(String email);

    /**
     * Checks if an organization with the given email address exists.
     *
     * @param email The email address to check.
     * @return True if an organization with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}