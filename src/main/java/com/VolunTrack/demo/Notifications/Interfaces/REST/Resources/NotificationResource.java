package com.VolunTrack.demo.Notifications.Interfaces.REST.Resources;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;

import java.time.LocalDateTime;

public record NotificationResource(
        Long id,
        NotificationType type,
        String title,
        String message,
        LocalDateTime createdAt,
        Long recipientId,
        RecipientType recipientType
) {
}