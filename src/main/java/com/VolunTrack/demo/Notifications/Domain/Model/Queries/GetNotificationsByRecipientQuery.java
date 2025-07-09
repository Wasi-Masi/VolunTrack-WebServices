package com.VolunTrack.demo.Notifications.Domain.Model.Queries;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the RecipientType enum to represent recipient types

public record GetNotificationsByRecipientQuery(Long recipientId, RecipientType recipientType) {
    /**
     * The GetNotificationsByRecipientQuery class represents a query to retrieve notifications for a specific recipient.
     * This query allows filtering notifications based on the recipient's ID and recipient type.
     * It is part of the **Query side** in the CQRS (Command Query Responsibility Segregation) architecture.
     */
}
