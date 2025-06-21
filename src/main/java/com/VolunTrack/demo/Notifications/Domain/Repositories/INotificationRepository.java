package com.VolunTrack.demo.Notifications.Domain.Repositories;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Importing the Notification aggregate
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the RecipientType enum to filter by recipient type
import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for persistence operations
import org.springframework.stereotype.Repository; // Spring annotation to mark this as a repository

import java.util.List;

/**
 * The INotificationRepository interface extends JpaRepository and provides methods for performing database operations on the Notification entity.
 * This interface includes methods for retrieving notifications based on recipient information.
 */
@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Retrieves a list of notifications for a specific recipient, filtered by recipient ID and recipient type.
     * This method queries the database for notifications associated with a particular recipient (e.g., volunteer or organization).
     * 
     * @param recipientId - The ID of the recipient to filter notifications by.
     * @param recipientType - The type of recipient (e.g., volunteer or organization) to filter notifications by.
     * @return A list of notifications associated with the given recipient ID and recipient type.
     */
    List<Notification> findByRecipientIdAndRecipientType(Long recipientId, RecipientType recipientType);
}
