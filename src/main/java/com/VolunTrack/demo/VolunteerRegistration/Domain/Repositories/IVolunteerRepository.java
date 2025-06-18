// src/main/java/com/VolunTrack/demo/VolunteerRegistration/Domain/Repositories/IVolunteerRepository.java
package com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Volunteer} entities.
 * Extends {@link JpaRepository} to inherit common CRUD operations provided by Spring Data JPA.
 * This interface also defines custom queries specific to Volunteer operations.
 */
public interface IVolunteerRepository extends JpaRepository<Volunteer, Long> {
    /**
     * Finds a volunteer by their DNI (National Identity Document).
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param dni The DNI of the volunteer.
     * @return An Optional containing the Volunteer if found, or empty otherwise.
     */
    Optional<Volunteer> findByDni(String dni);

    /**
     * Checks if a volunteer with the given DNI exists.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param dni The DNI to check.
     * @return True if a volunteer with the DNI exists, false otherwise.
     */
    boolean existsByDni(String dni);

    /**
     * Finds a volunteer by their email address.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param email The email address of the volunteer.
     * @return An Optional containing the Volunteer if found, or empty otherwise.
     */
    Optional<Volunteer> findByEmail(String email);

    /**
     * Checks if a volunteer with the given email address exists.
     * Spring Data JPA will automatically generate the implementation for this method.
     *
     * @param email The email address to check.
     * @return True if a volunteer with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}