package com.VolunTrack.demo.Notifications.Domain.Model.Commands;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Importing the NotificationType enum for different types of notifications
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the RecipientType enum for the types of recipients

/**
 * The CreateNotificationCommand class represents the command to create a new notification.
 * It is part of the Command side in CQRS (Command Query Responsibility Segregation) architecture.
 * This command encapsulates the necessary data for creating a notification in the system.
 */
public record CreateNotificationCommand(
        NotificationType type, // The type of the notification (e.g., alert, reminder, etc.)
        Long recipientId, // The ID of the recipient who will receive the notification
        RecipientType recipientType // The type of recipient (e.g., volunteer, organization)
) {
}
