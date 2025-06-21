package com.VolunTrack.demo.Notifications.Interfaces.REST.Resources;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Import for NotificationType enum
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Import for RecipientType enum

import java.time.LocalDateTime; // Import for LocalDateTime to handle date and time

/**
 * The NotificationResource class represents the notification data that will be returned from the server to the client.
 * This is used as a DTO (Data Transfer Object) to transfer notification data from the backend to the frontend or API client.
 */
public record NotificationResource(
        Long id, // The unique identifier of the notification
        NotificationType type, // The type of notification (e.g., SIGNUP, NEW_ACTIVITY, etc.)
        String title, // The title of the notification
        String message, // The message content of the notification
        LocalDateTime createdAt, // The date and time when the notification was created
        Long recipientId, // The ID of the recipient who receives the notification
        RecipientType recipientType // The type of recipient (e.g., VOLUNTEER, ORGANIZATION)
) {
    /**
     * Constructor is automatically generated for the `record` type.
     * This class serves as a representation of a notification that is ready to be sent to the client as part of an API response.
     */
}
