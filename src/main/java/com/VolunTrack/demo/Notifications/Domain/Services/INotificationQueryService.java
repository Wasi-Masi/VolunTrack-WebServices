package com.VolunTrack.demo.Notifications.Domain.Services;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery;
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery;

import java.util.List;

public interface INotificationQueryService {
    List<Notification> handle(GetAllNotificationsQuery query);
    List<Notification> handle(GetNotificationsByRecipientQuery query);
}