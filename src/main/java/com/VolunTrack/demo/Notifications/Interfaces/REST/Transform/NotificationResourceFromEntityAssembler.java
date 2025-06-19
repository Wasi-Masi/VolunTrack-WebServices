package com.VolunTrack.demo.Notifications.Interfaces.REST.Transform;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getRecipientId(),
                entity.getRecipientType()
        );
    }
}