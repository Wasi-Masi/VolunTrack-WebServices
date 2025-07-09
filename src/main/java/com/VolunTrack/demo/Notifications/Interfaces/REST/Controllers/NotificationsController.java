package com.VolunTrack.demo.Notifications.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService; // Importing the command service to handle notification commands
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationQueryService; // Importing the query service to handle notification queries
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand; // Importing the DeleteNotificationCommand for deletion operations
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Import for recipientType parameter
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetAllNotificationsQuery; // Import for querying all notifications
import com.VolunTrack.demo.Notifications.Domain.Model.Queries.GetNotificationsByRecipientQuery; // Import for querying notifications by recipient
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.CreateNotificationResource; // Importing the resource for creating notifications
import com.VolunTrack.demo.Notifications.Interfaces.REST.Resources.NotificationResource; // Importing the resource for notification details
import com.VolunTrack.demo.Notifications.Interfaces.REST.Transform.CreateNotificationCommandFromResourceAssembler; // Import for transforming the resource to a command
import com.VolunTrack.demo.Notifications.Interfaces.REST.Transform.NotificationResourceFromEntityAssembler; // Import for transforming the entity to resource
import io.swagger.v3.oas.annotations.Operation; // Swagger annotations for API documentation
import io.swagger.v3.oas.annotations.tags.Tag; // Swagger annotations for API documentation
import org.springframework.http.HttpStatus; // Spring's HttpStatus class to handle HTTP responses
import org.springframework.http.MediaType; // For setting response media type to JSON
import org.springframework.http.ResponseEntity; // Spring's ResponseEntity to return HTTP responses
import org.springframework.web.bind.annotation.*; // Spring's annotations for REST controllers

import java.util.List;
import java.util.stream.Collectors;

/**
 * The NotificationsController class handles the HTTP requests related to notifications.
 * It provides endpoints to create, retrieve, and delete notifications.
 * The controller interacts with the service layer (Command and Query Services) to execute business logic.
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Operations related to notifications")
public class NotificationsController {

    private final INotificationCommandService notificationCommandService; // Service to handle command operations (create, delete notifications)
    private final INotificationQueryService notificationQueryService;     // Service to handle query operations (get notifications)

    /**
     * Constructor to inject the services needed for command and query operations.
     *
     * @param notificationCommandService - Service to handle notification commands (e.g., create and delete).
     * @param notificationQueryService - Service to handle notification queries (e.g., get notifications).
     */
    public NotificationsController(INotificationCommandService notificationCommandService, INotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Endpoint to create a new notification in the system.
     *
     * @param resource - The resource containing the data for the notification to be created.
     * @return A ResponseEntity containing the created notification resource or a bad request if creation fails.
     */
    @Operation(summary = "Create a notification", description = "Creates a new notification in the system.")
    @PostMapping
    public ResponseEntity<NotificationResource> createNotification(@RequestBody CreateNotificationResource resource) {
        // Convert the incoming resource into a command
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        // Handle the command to create a new notification
        var notification = notificationCommandService.handle(command);
        // Return the created notification as a response or bad request if it fails
        return notification.map(value ->
                new ResponseEntity<>(NotificationResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint to retrieve a list of all notifications in the system.
     *
     * @return A ResponseEntity containing a list of all notifications.
     */
    @Operation(summary = "Get all notifications", description = "Retrieves a list of all registered notifications.")
    @GetMapping
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        var query = new GetAllNotificationsQuery(); // Create a query to get all notifications
        var notifications = notificationQueryService.handle(query); // Use the query service to fetch notifications
        // Transform the notifications into resources to send as the response
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationResources); // Return the notifications as the response
    }

    /**
     * Endpoint to retrieve a list of notifications filtered by recipient.
     *
     * @param recipientType - The type of recipient (e.g., volunteer, organization).
     * @param recipientId - The ID of the recipient.
     * @return A ResponseEntity containing a list of notifications for the specified recipient.
     */
    @Operation(summary = "Get notifications by recipient ID", description = "Retrieves a notification's details by its unique identifier.")
    @GetMapping("/byRecipient/{recipientType}/{recipientId}")
    public ResponseEntity<List<NotificationResource>> getNotificationsByRecipient(
            @PathVariable RecipientType recipientType,
            @PathVariable Long recipientId) {
        // Create a query to get notifications by recipient ID and type
        var query = new GetNotificationsByRecipientQuery(recipientId, recipientType);
        var notifications = notificationQueryService.handle(query); // Use the query service to fetch notifications
        // Transform the notifications into resources to send as the response
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationResources); // Return the notifications as the response
    }

    /**
     * Endpoint to delete a notification from the system.
     *
     * @param notificationId - The ID of the notification to be deleted.
     * @return A ResponseEntity indicating whether the deletion was successful.
     */
    @Operation(summary = "Delete a notification", description = "Deletes a notification from the system by its unique identifier.")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        try {
            // Create a command to delete the notification
            var command = new DeleteNotificationCommand(notificationId);
            notificationCommandService.handle(command); // Use the command service to delete the notification
            return ResponseEntity.noContent().build(); // Return no content to indicate successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().header("X-Error-Message", e.getMessage()).build(); // Return not found if the notification doesn't exist
        }
    }
}
