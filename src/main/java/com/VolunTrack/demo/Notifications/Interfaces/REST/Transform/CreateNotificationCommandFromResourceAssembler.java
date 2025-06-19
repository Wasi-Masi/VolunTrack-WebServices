package com.VolunTrack.demo.Notifications.Interfaces.REST.Transform;

import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                resource.type(),
                resource.recipientId(),
                resource.recipientType()
        );
    }
}