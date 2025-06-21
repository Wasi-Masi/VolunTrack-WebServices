package com.VolunTrack.demo.Notifications.Application.Internal.CommandServices;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Repositories.INotificationRepository;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import org.apache.tomcat.util.modeler.NotificationInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationCommandService implements INotificationCommandService {

    private final INotificationRepository notificationRepository;

    public NotificationCommandService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        NotificationType type = command.type();
        String title = type.getDefaultTitle();
        String message = type.getDefaultMessage();

        Notification newNotification = new Notification(
                type,
                title,
                message,
                command.recipientId(),
                command.recipientType()
        );
        return Optional.of(notificationRepository.save(newNotification));
    }

    @Override
    public void handle(DeleteNotificationCommand command) {
        if (!notificationRepository.existsById(command.notificationId())) {
            throw new IllegalArgumentException("Notification with ID " + command.notificationId() + " not found.");
        }
        notificationRepository.deleteById(command.notificationId());
    }
}