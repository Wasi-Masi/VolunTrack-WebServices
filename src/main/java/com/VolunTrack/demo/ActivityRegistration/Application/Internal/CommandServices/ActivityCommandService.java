package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity entity
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand; // Importing the CreateActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand; // Importing the DeleteActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand; // Importing the UpdateActivityCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository; // Importing the activity repository interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IActivityService; // Importing the activity service interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery; // Importing the GetAllActivitiesQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery; // Importing the GetActivityByIdQuery
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService; // Importing the notification command service
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand; // Importing the notification creation command
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Importing the notification type enum
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the recipient type enum
import org.springframework.stereotype.Service; // Importing Spring's Service annotation

import java.util.List;
import java.util.Optional;

/**
 * The ActivityCommandService class handles commands related to activities such as 
 * creating, updating, and deleting activities. It ensures that business rules are followed.
 * This service acts as an intermediary between the application layer and the domain layer.
 */
@Service
public class ActivityCommandService implements IActivityService {

    private final IActivityRepository activityRepository; // Repository to interact with activity data
    private final INotificationCommandService notificationCommandService; // Service to handle notifications

    /**
     * Constructor to inject dependencies for repository and notification service.
     * 
     * @param activityRepository - The repository for managing activity data.
     * @param notificationCommandService - The service for handling notifications.
     */
    public ActivityCommandService(IActivityRepository activityRepository,
                                  INotificationCommandService notificationCommandService) {
        this.activityRepository = activityRepository;
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Handles the creation of a new activity.
     * Checks if an activity with the same title already exists, and if not, creates a new one.
     * Also sends a notification to the organization about the new activity.
     * 
     * @param command - The command containing the data to create a new activity.
     * @return The newly created activity wrapped in an Optional.
     */
    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        // Check if an activity with the same title already exists
        if (activityRepository.findByTitulo(command.titulo()).isPresent()) {
            throw new IllegalArgumentException("Activity with title " + command.titulo() + " already exists.");
        }
        
        // Create a new activity from the command data
        Activity activity = new Activity(
                command.fecha(),
                command.horaInicio(),
                command.horaFin(),
                command.titulo(),
                command.descripcion(),
                command.instrucciones(),
                command.proposito(),
                command.cupos(),
                command.ubicacion(),
                command.estado(),
                command.organizacionId()
        );
        
        // Save the new activity to the repository
        Activity savedActivity = activityRepository.save(activity);

        // Send a notification to the organization about the new activity
        try {
            CreateNotificationCommand notificationCommand = new CreateNotificationCommand(
                    NotificationType.NEW_ACTIVITY,
                    (long) savedActivity.getOrganizacion_id(),
                    RecipientType.ORGANIZATION
            );
            notificationCommandService.handle(notificationCommand);
        } catch (Exception e) {
            System.err.println("Error creating new-activity notification for organization " + savedActivity.getOrganizacion_id() + ": " + e.getMessage());
        }

        return Optional.of(savedActivity);
    }

    /**
     * Handles the updating of an existing activity.
     * Retrieves the activity by its ID, updates the attributes with new values, and saves it back to the repository.
     * 
     * @param command - The command containing the data to update an activity.
     * @return The updated activity wrapped in an Optional, if found.
     */
    @Override
    public Optional<Activity> handle(UpdateActivityCommand command) {
        return activityRepository.findById(command.actividadId()).map(activity -> {
            // Update activity fields
            activity.setFecha(command.fecha());
            activity.setHoraInicio(command.horaInicio());
            activity.setHoraFin(command.horaFin());
            activity.setTitulo(command.titulo());
            activity.setDescripcion(command.descripcion());
            activity.setInstrucciones(command.instrucciones());
            activity.setProposito(command.proposito());
            activity.setCupos(command.cupos());
            activity.setUbicacion(command.ubicacion());
            activity.setEstado(command.estado());
            activity.setOrganizacion_id(command.organizacionId());
            // Save the updated activity
            return activityRepository.save(activity);
        });
    }

    /**
     * Handles the deletion of an activity.
     * Verifies if the activity exists and deletes it from the repository.
     * 
     * @param command - The command containing the ID of the activity to be deleted.
     */
    @Override
    public void handle(DeleteActivityCommand command) {
        // Check if the activity exists before attempting to delete
        if (!activityRepository.existsById(command.actividadId())) {
            throw new IllegalArgumentException("Activity with ID " + command.actividadId() + " not found.");
        }
        // Delete the activity by its ID
        activityRepository.deleteById(command.actividadId());
    }

    /**
     * This method is part of the query interface but is not supported in the command service.
     * Query operations should be handled by the ActivityQueryService.
     * 
     * @param query - The query to get all activities (not supported here).
     * @return An exception is thrown since this operation should be handled elsewhere.
     */
    @Override
    public List<Activity> handle(GetAllActivitiesQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }

    /**
     * This method is part of the query interface but is not supported in the command service.
     * Query operations should be handled by the ActivityQueryService.
     * 
     * @param query - The query to get an activity by its ID (not supported here).
     * @return An exception is thrown since this operation should be handled elsewhere.
     */
    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }
}
