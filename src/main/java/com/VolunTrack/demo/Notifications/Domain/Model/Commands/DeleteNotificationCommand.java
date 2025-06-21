package com.VolunTrack.demo.Notifications.Domain.Model.Commands;

public record DeleteNotificationCommand(Long notificationId) {
    /**
     * The DeleteNotificationCommand class represents the command to delete an existing notification.
     * This command encapsulates the necessary data to delete a specific notification from the system.
     * It is part of the Command side in CQRS (Command Query Responsibility Segregation) architecture.
     */
}
