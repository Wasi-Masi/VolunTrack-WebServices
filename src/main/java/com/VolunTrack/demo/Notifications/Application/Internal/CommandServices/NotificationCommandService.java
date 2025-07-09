package com.VolunTrack.demo.Notifications.Application.Internal.CommandServices;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService; // Importing the notification command service interface
import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Importing the Notification aggregate
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand; // Importing the CreateNotificationCommand
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand; // Importing the DeleteNotificationCommand
import com.VolunTrack.demo.Notifications.Domain.Repositories.INotificationRepository; // Importing the notification repository interface
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Importing the NotificationType enum
import org.springframework.stereotype.Service; // Spring annotation to indicate that this is a service class

import java.util.Optional;

/**
 * The NotificationCommandService class implements the INotificationCommandService interface and provides the logic
 * for handling notification-related command operations such as creating and deleting notifications.
 * It interacts with the repository to persist notifications in the database.
 */
@Service
public class NotificationCommandService implements INotificationCommandService {

    private final INotificationRepository notificationRepository; // Repository to interact with the notification data

    /**
     * Constructor to inject the notification repository dependency.
     *
     * @param notificationRepository - The repository to perform operations on notifications in the database.
     */
    public NotificationCommandService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Handles the creation of a new notification.
     * This method processes the CreateNotificationCommand and persists the new notification in the database.
     *
     * @param command - The command containing the details of the notification to be created.
     * @return An Optional containing the created Notification, or empty if the creation fails.
     */
    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        // Retrieve the default title and message based on the notification type
        NotificationType type = command.type();
        String title = type.getDefaultTitle();
        String message = type.getDefaultMessage();

        // Create a new Notification object using the provided details from the command
        Notification newNotification = new Notification(
                type, // The type of the notification (e.g., "SIGNUP", "NEW_ACTIVITY")
                title, // The title of the notification, based on the type
                message, // The message of the notification, based on the type
                command.recipientId(), // The recipient's ID
                command.recipientType() // The recipient's type (e.g., "VOLUNTEER", "ORGANIZATION")
        );

        // Save the newly created notification to the database and return it wrapped in an Optional
        return Optional.of(notificationRepository.save(newNotification));
    }

    /**
     * Handles the deletion of an existing notification.
     * This method processes the DeleteNotificationCommand and deletes the notification from the system.
     *
     * @param command - The command containing the ID of the notification to be deleted.
     */
    @Override
    public void handle(DeleteNotificationCommand command) {
        // Check if the notification exists in the database
        if (!notificationRepository.existsById(command.notificationId())) {
            // If the notification doesn't exist, throw an exception
            throw new IllegalArgumentException("Notification with ID " + command.notificationId() + " not found.");
        }

        // Delete the notification from the database by its ID
        notificationRepository.deleteById(command.notificationId());
    }
}
