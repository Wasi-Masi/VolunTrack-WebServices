package com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories;

import com.VolunTrack.demo.Shared.Infrastructure.Persistence.JPA.Repositories.BaseRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Volunteer} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 * This interface can also define custom queries specific to Volunteer operations.
 */
@Repository
public interface IVolunteerRepository extends BaseRepository<Volunteer, Long> {
    /**
     * Finds a volunteer by their DNI (National Identity Document).
     *
     * @param dni The DNI of the volunteer.
     * @return An Optional containing the Volunteer if found, or empty otherwise.
     */
    Optional<Volunteer> findByDni(String dni);

    /**
     * Checks if a volunteer with the given DNI exists.
     *
     * @param dni The DNI to check.
     * @return True if a volunteer with the DNI exists, false otherwise.
     */
    boolean existsByDni(String dni);

    /**
     * Finds a volunteer by their email address.
     *
     * @param email The email address of the volunteer.
     * @return An Optional containing the Volunteer if found, or empty otherwise.
     */
    Optional<Volunteer> findByEmail(String email);

    /**
     * Checks if a volunteer with the given email address exists.
     *
     * @param email The email address to check.
     * @return True if a volunteer with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}