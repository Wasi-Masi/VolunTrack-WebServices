package com.VolunTrack.demo.Notifications.Domain.Services;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Importing the Notification aggregate to return as result
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand; // Importing the CreateNotificationCommand to create a notification
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand; // Importing the DeleteNotificationCommand to delete a notification

import java.util.Optional;

/**
 * The INotificationCommandService interface defines the contract for handling command operations related to notifications.
 * This includes creating and deleting notifications in the system.
 */
public interface INotificationCommandService {

    /**
     * Handles the creation of a new notification.
     * This method processes the CreateNotificationCommand and returns an Optional containing the created notification if successful.
     *
     * @param command - The command containing the details of the notification to be created.
     * @return An Optional containing the created Notification, or empty if creation failed.
     */
    Optional<Notification> handle(CreateNotificationCommand command);

    /**
     * Handles the deletion of an existing notification.
     * This method processes the DeleteNotificationCommand and performs the necessary logic to delete the notification from the system.
     *
     * @param command - The command containing the ID of the notification to be deleted.
     */
    void handle(DeleteNotificationCommand command);
}
