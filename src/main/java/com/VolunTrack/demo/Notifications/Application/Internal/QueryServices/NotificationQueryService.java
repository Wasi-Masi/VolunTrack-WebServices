package com.VolunTrack.demo.Notifications.Application.Internal.QueryServices;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationQueryService;
import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery;
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery;
import com.VolunTrack.demo.Notifications.Domain.Repositories.INotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryService implements INotificationQueryService {

    private final INotificationRepository notificationRepository;

    public NotificationQueryService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> handle(GetNotificationsByRecipientQuery query) {
        return notificationRepository.findByRecipientIdAndRecipientType(query.recipientId(), query.recipientType());
    }
}