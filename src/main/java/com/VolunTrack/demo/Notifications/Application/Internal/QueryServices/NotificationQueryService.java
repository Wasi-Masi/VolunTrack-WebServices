package com.VolunTrack.demo.Notifications.Application.Internal.QueryServices;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationQueryService; // Importing the NotificationQueryService interface
import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification; // Importing the Notification aggregate
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery; // Importing the query to get all notifications
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery; // Importing the query to get notifications by recipient
import com.VolunTrack.demo.Notifications.Domain.Repositories.INotificationRepository; // Importing the Notification repository interface
import org.springframework.stereotype.Service; // Spring annotation to mark this class as a service

import java.util.List;

/**
 * The NotificationQueryService class implements the INotificationQueryService interface and provides the logic
 * for handling query operations related to notifications, such as retrieving all notifications or filtering them
 * by recipient.
 * It interacts with the repository to retrieve data based on the provided queries.
 */
@Service
public class NotificationQueryService implements INotificationQueryService {

    private final INotificationRepository notificationRepository; // Repository to interact with the notification data

    /**
     * Constructor to inject the notification repository dependency.
     *
     * @param notificationRepository - The repository for managing notification data.
     */
    public NotificationQueryService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Handles the retrieval of all notifications in the system.
     * This method processes the GetAllNotificationsQuery and returns a list of all notifications.
     *
     * @param query - The query to get all notifications.
     * @return A list of all notifications in the system.
     */
    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return notificationRepository.findAll(); // Retrieve all notifications from the repository
    }

    /**
     * Handles the retrieval of notifications for a specific recipient.
     * This method processes the GetNotificationsByRecipientQuery and filters notifications based on recipient ID and recipient type.
     *
     * @param query - The query containing the recipient ID and recipient type to filter notifications.
     * @return A list of notifications for the specified recipient.
     */
    @Override
    public List<Notification> handle(GetNotificationsByRecipientQuery query) {
        // Retrieve notifications for the given recipient ID and recipient type from the repository
        return notificationRepository.findByRecipientIdAndRecipientType(query.recipientId(), query.recipientType());
    }
}
