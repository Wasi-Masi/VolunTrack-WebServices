package com.VolunTrack.demo.ActivityRegistration.Domain.Repositories;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity aggregate
import org.springframework.data.jpa.repository.JpaRepository; // Importing Spring Data JPA's JpaRepository for CRUD operations
import org.springframework.stereotype.Repository; // Importing the Spring annotation for repository marking

import java.util.Optional;

/**
 * The IActivityRepository interface extends JpaRepository to provide CRUD operations for the Activity entity.
 * It also defines a custom query method to find an activity by its title.
 * This interface interacts with the database and provides the necessary methods for managing activities.
 */
@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Finds an activity by its title.
     *
     * @param titulo - The title of the activity.
     * @return An Optional containing the activity if found, or empty if not found.
     */
    Optional<Activity> findByTitulo(String titulo); // Custom query method to find an activity by title
}
