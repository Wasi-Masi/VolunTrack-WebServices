package com.VolunTrack.demo.Notifications.Domain.Model.Commands;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;

public record CreateNotificationCommand(
        NotificationType type,
        Long recipientId,
        RecipientType recipientType
) {
}