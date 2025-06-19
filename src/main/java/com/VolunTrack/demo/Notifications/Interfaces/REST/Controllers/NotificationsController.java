package com.VolunTrack.demo.Notifications.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationQueryService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Import for recipientType parameter
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery;
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.CreateNotificationResource;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.NotificationResource;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Transform.CreateNotificationCommandFromResourceAssembler;
import com.VolunTrack.demo.Notifications.Interfaces.REST.Transform.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Operations related to notifications")
public class NotificationsController {

    private final INotificationCommandService notificationCommandService;
    private final INotificationQueryService notificationQueryService;

    public NotificationsController(INotificationCommandService notificationCommandService, INotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    @Operation(summary = "Create a notification", description = "Creates a new notification in the system.")
    @PostMapping
    public ResponseEntity<NotificationResource> createNotification(@RequestBody CreateNotificationResource resource) {
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var notification = notificationCommandService.handle(command);
        return notification.map(value ->
                new ResponseEntity<>(NotificationResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get all notifications", description = "Retrieves a list of all registered notifications.")
    @GetMapping
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        var query = new GetAllNotificationsQuery();
        var notifications = notificationQueryService.handle(query);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationResources);
    }

    @Operation(summary = "Get notifications by recipient ID", description = "Retrieves a notification's details by its unique identifier.")
    @GetMapping("/byRecipient/{recipientType}/{recipientId}")
    public ResponseEntity<List<NotificationResource>> getNotificationsByRecipient(
            @PathVariable RecipientType recipientType,
            @PathVariable Long recipientId) {
        var query = new GetNotificationsByRecipientQuery(recipientId, recipientType);
        var notifications = notificationQueryService.handle(query);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationResources);
    }

    @Operation(summary = "Delete a notification", description = "Deletes a notification from the system by its unique identifier.")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        try {
            var command = new DeleteNotificationCommand(notificationId);
            notificationCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("X-Error-Message", e.getMessage()).build();
        }
    }
}