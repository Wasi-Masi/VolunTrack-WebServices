package com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand; // Importing CreateActivityCommand for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand; // Importing DeleteActivityCommand for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand; // Importing UpdateActivityCommand for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery; // Importing GetAllActivitiesQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery; // Importing GetActivityByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository; // Importing the Activity repository interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IActivityService; // Importing the Activity service interface
import org.springframework.stereotype.Service; // Spring's annotation for marking this class as a service

import java.util.List;
import java.util.Optional;

/**
 * The ActivityQueryService class handles query operations related to activities.
 * It is part of the read model in a CQRS (Command Query Responsibility Segregation) architecture.
 * This service interacts with the repository to retrieve activities based on the provided queries.
 */
@Service
public class ActivityQueryService implements IActivityService { // Implements IActivityService

    private final IActivityRepository activityRepository; // Repository to interact with activity data

    /**
     * Constructor to inject the activity repository dependency.
     *
     * @param activityRepository - The repository for managing activity data.
     */
    public ActivityQueryService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Handles the retrieval of all activities from the repository.
     * This method handles the query to fetch all activities in the system.
     *
     * @param query - The query to get all activities.
     * @return A list of all activities.
     */
    @Override
    public List<Activity> handle(GetAllActivitiesQuery query) {
        return activityRepository.findAll(); // Retrieve all activities from the repository
    }

    /**
     * Handles the retrieval of a specific activity by its ID.
     * This method handles the query to fetch a single activity based on its unique ID.
     *
     * @param query - The query containing the ID of the activity to fetch.
     * @return An Optional containing the activity if found, or empty if not found.
     */
    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        return activityRepository.findById(query.actividadId()); // Retrieve activity by ID from the repository
    }

    /**
     * This method handles command operations, but these operations should be handled by the ActivityCommandService.
     * Therefore, these methods throw UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to create a new activity.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }

    /**
     * This method handles command operations, but these operations should be handled by the ActivityCommandService.
     * Therefore, this method throws UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to update an existing activity.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public Optional<Activity> handle(UpdateActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }

    /**
     * This method handles command operations, but these operations should be handled by the ActivityCommandService.
     * Therefore, this method throws UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to delete an activity.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public void handle(DeleteActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }
}
