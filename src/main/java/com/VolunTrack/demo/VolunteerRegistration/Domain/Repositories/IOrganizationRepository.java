package com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link Organization} entities.
 * Extends {@link JpaRepository} to inherit common CRUD operations provided by Spring Data JPA.
 * This interface also defines custom queries specific to Organization operations.
 */
public interface IOrganizationRepository extends JpaRepository<Organization, Long> {
    /**
     * Finds an organization by its email address.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param email The email address of the organization.
     * @return An Optional containing the Organization if found, or empty otherwise.
     */
    Optional<Organization> findByEmail(String email);

    /**
     * Checks if an organization with the given email address exists.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param email The email address to check.
     * @return True if an organization with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}