package com.VolunTrack.demo.Notifications.Domain.Model.Queries;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;

public record GetNotificationsByRecipientQuery(Long recipientId, RecipientType recipientType) {
}