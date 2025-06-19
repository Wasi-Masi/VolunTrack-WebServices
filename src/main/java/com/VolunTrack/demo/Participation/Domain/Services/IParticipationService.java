package com.VolunTrack.demo.Participation.Domain.Services;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.ParticipationStatus;
import java.util.List;
import java.util.Optional;

/**
 * Domain Service interface for Participation-related business operations.
 * This service handles logic that might involve multiple aggregates or complex domain rules.
 */
public interface IParticipationService {

    /**
     * Creates a new participation record.
     *
     * @param volunteerId The ID of the volunteer.
     * @param activityId The ID of the activity.
     * @param initialStatus The initial status of the participation (e.g., REGISTERED).
     * @return An Optional containing the created Participation if successful, or empty if validation fails (e.g., volunteer/activity not found, or duplicate participation).
     */
    Optional<Participation> createParticipation(Long volunteerId, Long activityId, ParticipationStatus initialStatus);

    /**
     * Retrieves a participation record by its ID.
     *
     * @param participationId The ID of the participation record.
     * @return An Optional containing the Participation if found, or empty otherwise.
     */
    Optional<Participation> getParticipationById(Long participationId);

    /**
     * Retrieves all participation records for a specific volunteer.
     *
     * @param volunteerId The ID of the volunteer.
     * @return A list of Participation records for the given volunteer.
     */
    List<Participation> getParticipationsByVolunteerId(Long volunteerId);

    /**
     * Retrieves all participation records for a specific activity.
     *
     * @param activityId The ID of the activity.
     * @return A list of Participation records for the given activity.
     */
    List<Participation> getParticipationsByActivityId(Long activityId);

    /**
     * Updates the status of an existing participation record.
     *
     * @param participationId The ID of the participation to update.
     * @param newStatus The new status for the participation.
     * @return An Optional containing the updated Participation if found and updated, or empty if not found.
     */
    Optional<Participation> updateParticipationStatus(Long participationId, ParticipationStatus newStatus);

    /**
     * Deletes a participation record by its ID.
     *
     * @param participationId The ID of the participation to delete.
     * @return True if the participation was deleted successfully, false otherwise.
     */
    boolean deleteParticipation(Long participationId);
}