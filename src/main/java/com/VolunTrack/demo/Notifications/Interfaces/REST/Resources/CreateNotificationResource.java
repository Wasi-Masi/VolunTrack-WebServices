package com.VolunTrack.demo.Notifications.Interfaces.REST.Resources;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Import for NotificationType enum
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Import for RecipientType enum

/**
 * The CreateNotificationResource class represents the data needed to create a new notification.
 * This is used as a DTO (Data Transfer Object) for transferring data from the client to the server.
 */
public record CreateNotificationResource(
        NotificationType type, // The type of notification (e.g., SIGNUP, NEW_ACTIVITY)
        Long recipientId, // The ID of the recipient who will receive the notification
        RecipientType recipientType // The type of the recipient (e.g., VOLUNTEER, ORGANIZATION)
) {
    /**
     * Constructor is automatically generated for the `record` type.
     * This class is used to encapsulate the necessary data for creating a new notification.
     * It provides a clean and simple way to transfer the data between the client and the backend service.
     */
}
