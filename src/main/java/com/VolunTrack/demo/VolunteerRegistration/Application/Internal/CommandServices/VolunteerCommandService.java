package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Services.IVolunteerService;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Command service for managing Volunteer-related operations.
 * This service handles incoming commands, delegates business logic to the domain service,
 * and ensures that the Unit of Work is managed.
 */
@Service
public class VolunteerCommandService {

    private final IVolunteerService volunteerService;
    private final INotificationCommandService notificationCommandService;

    /**
     * Constructs a new VolunteerCommandService.
     *
     * @param volunteerService The domain service for volunteers.
     */
    public VolunteerCommandService(IVolunteerService volunteerService, INotificationCommandService notificationCommandService) {
        this.volunteerService = volunteerService;
        this.notificationCommandService = notificationCommandService;

    }

    /**
     * Handles the creation of a new volunteer based on the provided command.
     *
     * @param command The command containing the data for the new volunteer.
     * @return An Optional containing the created Volunteer if successful, otherwise empty.
     */
    public Optional<Volunteer> handle(CreateVolunteerCommand command) {
        Optional<Volunteer> createdVolunteer = volunteerService.createVolunteer(
                command.firstName(),
                command.lastName(),
                command.dni(),
                command.dateOfBirth(),
                command.email(),
                command.phoneNumber(),
                command.address(),
                command.organizationId(),
                command.profession()
        );

        createdVolunteer.ifPresent(volunteer -> {
            try {
                CreateNotificationCommand notificationCommand = new CreateNotificationCommand(
                        NotificationType.SIGNUP,
                        volunteer.getId(),
                        RecipientType.VOLUNTEER
                );
                notificationCommandService.handle(notificationCommand);
            } catch (Exception e) {
                System.err.println("Error creating signup notification for volunteer " + volunteer.getId() + ": " + e.getMessage());
            }
        });

        return createdVolunteer;
    }

    /**
     * Handles the update of an existing volunteer based on the provided command.
     *
     * @param command The command containing the data for the volunteer update.
     * @return An Optional containing the updated Volunteer if found and updated, otherwise empty.
     */
    public Optional<Volunteer> handle(UpdateVolunteerCommand command) {
        return volunteerService.updateVolunteer(
                command.volunteerId(),
                command.firstName().orElse(null),
                command.lastName().orElse(null),
                command.dni().orElse(null),
                command.dateOfBirth().orElse(null),
                command.email().orElse(null),
                command.phoneNumber().orElse(null),
                command.address().orElse(null),
                command.profession().orElse(null),
                command.status().orElse(null)
        );
    }

    /**
     * Handles the deletion of an existing volunteer based on the provided command.
     *
     * @param command The command containing the ID of the volunteer to delete.
     * @return True if the volunteer was deleted successfully, false otherwise.
     */
    public boolean handle(DeleteVolunteerCommand command) {
        return volunteerService.deleteVolunteer(command.volunteerId());
    }
}