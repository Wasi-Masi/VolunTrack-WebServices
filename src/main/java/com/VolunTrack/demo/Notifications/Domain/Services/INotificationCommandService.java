package com.VolunTrack.demo.Notifications.Domain.Services;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand;

import java.util.Optional;

public interface INotificationCommandService {
    Optional<Notification> handle(CreateNotificationCommand command);
    void handle(DeleteNotificationCommand command);
}