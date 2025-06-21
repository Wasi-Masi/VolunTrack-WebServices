package com.VolunTrack.demo.ActivityRegistration.Domain.Repositories;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription; // Importing the Inscription aggregate
import org.springframework.data.jpa.repository.JpaRepository; // Importing Spring Data JPA's JpaRepository for CRUD operations
import org.springframework.stereotype.Repository; // Importing the Spring annotation for repository marking

import java.util.List;
import java.util.Optional;

/**
 * The IInscriptionRepository interface extends JpaRepository to provide CRUD operations for the Inscription entity.
 * It also defines custom query methods to find inscriptions by volunteer ID, activity ID, or both.
 * This interface interacts with the database and provides the necessary methods for managing inscriptions.
 */
@Repository
public interface IInscriptionRepository extends JpaRepository<Inscription, Long> {

    /**
     * Finds inscriptions by volunteer ID.
     * 
     * @param voluntarioId - The ID of the volunteer.
     * @return A list of inscriptions for the specified volunteer.
     */
    List<Inscription> findByVoluntarioId(Long voluntarioId); // Custom query method to find inscriptions by volunteer ID

    /**
     * Finds inscriptions by activity ID.
     * 
     * @param actividadId - The ID of the activity.
     * @return A list of inscriptions for the specified activity.
     */
    List<Inscription> findByActividadId(Long actividadId); // Custom query method to find inscriptions by activity ID

    /**
     * Finds a specific inscription by both volunteer ID and activity ID.
     * 
     * @param voluntarioId - The ID of the volunteer.
     * @param actividadId - The ID of the activity.
     * @return An Optional containing the inscription if found, or empty if not found.
     */
    Optional<Inscription> findByVoluntarioIdAndActividadId(Long voluntarioId, Long actividadId); // Custom query method to find inscriptions by both volunteer and activity ID
}
