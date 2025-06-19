package com.VolunTrack.demo.Notifications.Interfaces.REST.Resources;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;

public record CreateNotificationResource(
        NotificationType type,
        Long recipientId,
        RecipientType recipientType
) {
}