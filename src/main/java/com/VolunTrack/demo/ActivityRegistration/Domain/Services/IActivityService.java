package com.VolunTrack.demo.ActivityRegistration.Domain.Services;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand; // Importing CreateActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand; // Importing DeleteActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand; // Importing UpdateActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery; // Importing GetAllActivitiesQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery; // Importing GetActivityByIdQuery

import java.util.List;
import java.util.Optional;

/**
 * The IActivityService interface defines the contract for the application services layer.
 * It combines both **command** and **query** operations related to activities in the system.
 * This interface ensures that the necessary methods for handling activities are defined,
 * and that business rules are applied consistently when interacting with activity data.
 * In a CQRS architecture, this interface would be implemented by a command service (for write operations)
 * and a query service (for read operations).
 */
public interface IActivityService {

    /**
     * Handles the creation of a new activity.
     * 
     * @param command - The command containing the data to create a new activity.
     * @return An Optional containing the created activity if successful, or empty if not.
     */
    Optional<Activity> handle(CreateActivityCommand command);

    /**
     * Handles the updating of an existing activity.
     * 
     * @param command - The command containing the data to update an activity.
     * @return An Optional containing the updated activity if successful, or empty if not found.
     */
    Optional<Activity> handle(UpdateActivityCommand command);

    /**
     * Handles the deletion of an activity.
     * 
     * @param command - The command containing the ID of the activity to delete.
     */
    void handle(DeleteActivityCommand command);

    /**
     * Handles the retrieval of all activities.
     * 
     * @param query - The query to get all activities.
     * @return A list of all activities in the system.
     */
    List<Activity> handle(GetAllActivitiesQuery query);

    /**
     * Handles the retrieval of an activity by its unique ID.
     * 
     * @param query - The query containing the ID of the activity to fetch.
     * @return An Optional containing the activity if found, or empty if not found.
     */
    Optional<Activity> handle(GetActivityByIdQuery query);
}
