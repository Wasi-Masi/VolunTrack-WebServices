package com.VolunTrack.demo.Notifications.Interfaces.REST.Transform;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Import for Notification aggregate
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.NotificationResource; // Import for NotificationResource to return as the DTO

/**
 * The NotificationResourceFromEntityAssembler class is responsible for converting a Notification entity
 * into a NotificationResource. This transformation allows the data stored in the backend (entity) to be mapped
 * into a format (resource) suitable for sending as a response to the client.
 */
public class NotificationResourceFromEntityAssembler {

    /**
     * Converts a Notification entity to a NotificationResource.
     * This method takes the Notification entity, which is typically stored in the database,
     * and converts it into a resource that will be sent back to the client.
     *
     * @param entity - The Notification entity that contains the data to be transformed.
     * @return A NotificationResource that contains the notification data in a format suitable for API responses.
     */
    public static NotificationResource toResourceFromEntity(Notification entity) {
        // Mapping the data from the entity to the resource
        return new NotificationResource(
                entity.getId(), // Notification ID
                entity.getType(), // Type of notification (e.g., SIGNUP, NEW_ACTIVITY)
                entity.getTitle(), // The title of the notification
                entity.getMessage(), // The message content of the notification
                entity.getCreatedAt(), // Date and time when the notification was created
                entity.getRecipientId(), // The recipient ID for the notification
                entity.getRecipientType() // The recipient type (e.g., VOLUNTEER, ORGANIZATION)
        );
    }
}
