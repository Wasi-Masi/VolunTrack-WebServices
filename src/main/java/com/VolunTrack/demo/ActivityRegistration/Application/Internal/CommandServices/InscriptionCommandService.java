package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription; // Importing the Inscription aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand; // Importing the CreateInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand; // Importing the DeleteInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand; // Importing the UpdateInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery; // Importing the GetAllInscriptionsQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery; // Importing the GetInscriptionByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery; // Importing the GetInscriptionsByActivityIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository; // Importing the Activity repository interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository; // Importing the Inscription repository interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IInscriptionService; // Importing the Inscription service interface
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository; // Importing the Volunteer repository interface
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService; // Importing the Notification service
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand; // Importing the CreateNotificationCommand
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Importing the NotificationType enum
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the RecipientType enum
import org.springframework.stereotype.Service; // Spring annotation for marking this as a service
import org.springframework.transaction.annotation.Transactional; // Annotation for handling transactions

import java.util.List;
import java.util.Optional;

/**
 * The InscriptionCommandService class handles commands related to volunteer inscriptions,
 * such as creating, updating, and deleting inscriptions. It ensures that business rules are followed.
 * This service interacts with the domain layer and repositories to perform the required operations.
 */
@Service
public class InscriptionCommandService implements IInscriptionService {

    private final IInscriptionRepository inscriptionRepository; // Repository to manage inscriptions
    private final IVolunteerRepository volunteerRepository; // Repository to manage volunteers
    private final IActivityRepository activityRepository; // Repository to manage activities
    private final INotificationCommandService notificationCommandService; // Service to handle notifications

    /**
     * Constructor to inject dependencies for repositories and notification service.
     *
     * @param inscriptionRepository - The repository for managing inscriptions
     * @param volunteerRepository - The repository for managing volunteers
     * @param activityRepository - The repository for managing activities
     * @param notificationCommandService - The service to handle notifications
     */
    public InscriptionCommandService(IInscriptionRepository inscriptionRepository,
                                     IVolunteerRepository volunteerRepository,
                                     IActivityRepository activityRepository,
                                     INotificationCommandService notificationCommandService) {
        this.inscriptionRepository = inscriptionRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Handles the creation of a new inscription.
     * Checks if the volunteer and the activity exist, if the volunteer is not already enrolled, 
     * and if there are available slots in the activity. Then, it creates the inscription and sends notifications.
     *
     * @param command - The command containing the data to create a new inscription.
     * @return The newly created inscription wrapped in an Optional.
     */
    @Override
    @Transactional
    public Optional<Inscription> handle(CreateInscriptionCommand command) {

        // Check if the volunteer exists
        if (!volunteerRepository.existsById(command.voluntarioId())) {
            throw new IllegalArgumentException("Volunteer with ID " + command.voluntarioId() + " does not exist.");
        }

        // Check if the activity exists
        Activity activity = activityRepository.findById(command.actividadId())
                .orElseThrow(() -> new IllegalArgumentException("Activity with ID " + command.actividadId() + " does not exist."));

        // Check if the volunteer is already enrolled in the activity
        if (inscriptionRepository.findByVoluntarioIdAndActividadId(command.voluntarioId(), command.actividadId()).isPresent()) {
            throw new IllegalArgumentException("Volunteer " + command.voluntarioId() + " is already enrolled in activity " + command.actividadId());
        }

        // Check if there are available slots in the activity
        if (!activity.tryIncrementInscriptionsActuales()) {
            throw new IllegalStateException("No available slots for activity with ID " + command.actividadId());
        }

        // Create a new inscription
        Inscription inscription = new Inscription(
                command.voluntarioId(),
                command.estado(),
                command.fecha(),
                command.actividadId()
        );

        // Save the activity and inscription in the repository
        activityRepository.save(activity);
        Inscription savedInscription = inscriptionRepository.save(inscription);

        // Send notification to the volunteer
        try {
            CreateNotificationCommand volunteerNotification = new CreateNotificationCommand(
                    NotificationType.JOINED_ACTIVITY,
                    savedInscription.getVoluntarioId(),
                    RecipientType.VOLUNTEER
            );
            notificationCommandService.handle(volunteerNotification);
        } catch (Exception e) {
            System.err.println("Error creating joined-activity notification for volunteer " + savedInscription.getVoluntarioId() + ": " + e.getMessage());
        }

        // Send notification to the organization
        try {
            CreateNotificationCommand organizationNotification = new CreateNotificationCommand(
                    NotificationType.VOLUNTEER_JOINED,
                    (long) activity.getOrganizacion_id(),
                    RecipientType.ORGANIZATION
            );
            notificationCommandService.handle(organizationNotification);
        } catch (Exception e) {
            System.err.println("Error creating volunteer-joined notification for organization " + activity.getOrganizacion_id() + ": " + e.getMessage());
        }

        return Optional.of(savedInscription);
    }

    /**
     * Handles the updating of an existing inscription.
     * Retrieves the inscription by its ID, updates its attributes, and saves it back to the repository.
     *
     * @param command - The command containing the data to update an inscription.
     * @return The updated inscription wrapped in an Optional, if found.
     */
    @Override
    public Optional<Inscription> handle(UpdateInscriptionCommand command) {
        return inscriptionRepository.findById(command.inscriptionId()).map(inscription -> {
            // Update inscription fields
            inscription.setVoluntarioId(command.voluntarioId());
            inscription.setEstado(command.estado());
            inscription.setFecha(command.fecha());
            inscription.setActividadId(command.actividadId());
            // Save the updated inscription
            return inscriptionRepository.save(inscription);
        });
    }

    /**
     * Handles the deletion of an inscription.
     * Verifies if the inscription exists and deletes it from the repository. Also, decreases the activity's current inscriptions.
     *
     * @param command - The command containing the ID of the inscription to be deleted.
     */
    @Override
    @Transactional
    public void handle(DeleteInscriptionCommand command) {
        // Check if the inscription exists
        Inscription inscriptionToDelete = inscriptionRepository.findById(command.inscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Enrollment with ID " + command.inscriptionId() + " not found."));

        // Retrieve the associated activity
        Activity activity = activityRepository.findById(inscriptionToDelete.getActividadId())
                .orElseThrow(() -> new IllegalStateException("Associated activity not found for inscription ID " + inscriptionToDelete.getInscription_id()));

        // Decrement the current number of inscriptions in the activity
        activity.tryDecrementInscriptionsActuales();
        activityRepository.save(activity);

        // Delete the inscription
        inscriptionRepository.deleteById(command.inscriptionId());
    }

    /**
     * This method is part of the query interface but is not supported in the command service.
     * Query operations should be handled by the InscriptionQueryService.
     *
     * @param query - The query to get all inscriptions (not supported here).
     * @return An exception is thrown since this operation should be handled elsewhere.
     */
    @Override
    public List<Inscription> handle(GetAllInscriptionsQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }

    /**
     * This method is part of the query interface but is not supported in the command service.
     * Query operations should be handled by the InscriptionQueryService.
     *
     * @param query - The query to get an inscription by its ID (not supported here).
     * @return An exception is thrown since this operation should be handled elsewhere.
     */
    @Override
    public Optional<Inscription> handle(GetInscriptionByIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }

    /**
     * This method is part of the query interface but is not supported in the command service.
     * Query operations should be handled by the InscriptionQueryService.
     *
     * @param query - The query to get inscriptions by activity ID (not supported here).
     * @return An exception is thrown since this operation should be handled elsewhere.
     */
    @Override
    public List<Inscription> handle(GetInscriptionsByActivityIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }
}
