package com.VolunTrack.demo.Notifications.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationQueryService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.DeleteNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
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

// Nuevas importaciones para ApiResponse y MessageSource
import com.VolunTrack.demo.response.ApiResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * REST controller for managing Notification entities.
 * This class provides endpoints for creating, retrieving, and deleting notifications,
 * interacting with INotificationCommandService and INotificationQueryService.
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Operations related to notifications")
public class NotificationsController {

    private final INotificationCommandService notificationCommandService;
    private final INotificationQueryService notificationQueryService;
    private final MessageSource messageSource;

    /**
     * Constructor to inject the services needed for command and query operations.
     *
     * @param notificationCommandService - Service to handle notification commands (e.g., create and delete).
     * @param notificationQueryService - Service to handle notification queries (e.g., get notifications).
     * @param messageSource - The service for resolving messages, with support for internationalization.
     */
    public NotificationsController(INotificationCommandService notificationCommandService,
                                   INotificationQueryService notificationQueryService,
                                   MessageSource messageSource) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
        this.messageSource = messageSource;
    }

    /**
     * Endpoint to create a new notification in the system.
     *
     * @param resource - The resource containing the data for the notification to be created.
     * @return A ResponseEntity containing the created notification resource or a bad request if creation fails.
     */
    @Operation(summary = "Create a notification", description = "Creates a new notification in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResource>> createNotification(@RequestBody CreateNotificationResource resource) {
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var notification = notificationCommandService.handle(command);
        return notification.map(value ->
                new ResponseEntity<>(
                        ApiResponse.success(NotificationResourceFromEntityAssembler.toResourceFromEntity(value),
                                messageSource.getMessage("notification.create.success", null, LocaleContextHolder.getLocale())),
                        HttpStatus.CREATED
                )
        ).orElseGet(() -> ResponseEntity.badRequest().body(
                ApiResponse.<NotificationResource>error(
                        messageSource.getMessage("notification.create.error.failed_no_result", null, LocaleContextHolder.getLocale()), null))
        );
    }

    /**
     * Endpoint to retrieve a list of all notifications in the system.
     *
     * @return A ResponseEntity containing a list of all notifications.
     */
    @Operation(summary = "Get all notifications", description = "Retrieves a list of all registered notifications.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResource>>> getAllNotifications() {
        var query = new GetAllNotificationsQuery();
        var notifications = notificationQueryService.handle(query);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(notificationResources,
                messageSource.getMessage("notification.get_all.success", null, LocaleContextHolder.getLocale())));
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
    public ResponseEntity<ApiResponse<List<NotificationResource>>> getNotificationsByRecipient(
            @PathVariable RecipientType recipientType,
            @PathVariable Long recipientId) {
        var query = new GetNotificationsByRecipientQuery(recipientId, recipientType);
        var notifications = notificationQueryService.handle(query);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        if (notificationResources.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(notificationResources,
                    messageSource.getMessage("notification.get_by_recipient.no_data", new Object[]{recipientType.toString(), recipientId}, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok(ApiResponse.success(notificationResources,
                messageSource.getMessage("notification.get_by_recipient.success", null, LocaleContextHolder.getLocale())));
    }

    /**
     * Endpoint to delete a notification from the system.
     *
     * @param notificationId - The ID of the notification to be deleted.
     * @return A ResponseEntity indicating whether the deletion was successful.
     */
    @Operation(summary = "Delete a notification", description = "Deletes a notification from the system by its unique identifier.")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable Long notificationId) {
        try {
            var command = new DeleteNotificationCommand(notificationId);
            notificationCommandService.handle(command);
            return ResponseEntity.ok(ApiResponse.noContent(
                    messageSource.getMessage("notification.delete.success", null, LocaleContextHolder.getLocale())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(messageSource.getMessage("notification.delete.not_found", new Object[]{notificationId}, LocaleContextHolder.getLocale()), null)
            );
        }
    }
}