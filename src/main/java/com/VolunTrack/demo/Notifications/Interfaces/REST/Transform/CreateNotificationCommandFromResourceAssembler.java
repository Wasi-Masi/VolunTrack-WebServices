package com.VolunTrack.demo.Notifications.Interfaces.REST.Transform;

import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand; // Importing the CreateNotificationCommand for the command object
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.CreateNotificationResource; // Importing the resource to be converted

/**
 * The CreateNotificationCommandFromResourceAssembler class is responsible for converting a CreateNotificationResource
 * into a CreateNotificationCommand. This transformation allows the data received from the client (in the form of a resource)
 * to be mapped to a command that can be processed by the business logic layer.
 */
public class CreateNotificationCommandFromResourceAssembler {

    /**
     * Converts a CreateNotificationResource into a CreateNotificationCommand.
     * This method takes the resource provided by the client and converts it into a command
     * that can be handled by the command service layer to create a notification in the system.
     *
     * @param resource - The CreateNotificationResource that contains the notification data.
     * @return A CreateNotificationCommand that encapsulates the necessary data to create the notification.
     */
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        // Mapping the data from the resource to the command
        return new CreateNotificationCommand(
                resource.type(),        // Notification type (e.g., NEW_ACTIVITY)
                resource.recipientId(), // Recipient ID (the person/entity receiving the notification)
                resource.recipientType() // Recipient type (e.g., VOLUNTEER, ORGANIZATION)
        );
    }
}
