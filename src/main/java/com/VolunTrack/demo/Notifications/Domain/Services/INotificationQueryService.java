package com.VolunTrack.demo.Notifications.Domain.Services;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Importing the Notification aggregate to return as result
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery; // Importing the query to get all notifications
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery; // Importing the query to get notifications by recipient

import java.util.List;

/**
 * The INotificationQueryService interface defines the contract for handling query operations related to notifications.
 * This includes fetching all notifications or filtering notifications by recipient information.
 */
public interface INotificationQueryService {

    /**
     * Handles the retrieval of all notifications in the system.
     * This method processes the GetAllNotificationsQuery and returns a list of all notifications.
     *
     * @param query - The query to get all notifications.
     * @return A list of all notifications in the system.
     */
    List<Notification> handle(GetAllNotificationsQuery query);

    /**
     * Handles the retrieval of notifications for a specific recipient.
     * This method processes the GetNotificationsByRecipientQuery and filters notifications based on recipient ID and recipient type.
     *
     * @param query - The query containing the recipient ID and recipient type to filter notifications.
     * @return A list of notifications for the specified recipient.
     */
    List<Notification> handle(GetNotificationsByRecipientQuery query);
}
