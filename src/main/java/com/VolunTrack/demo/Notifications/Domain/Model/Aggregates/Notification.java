package com.VolunTrack.demo.Notifications.Domain.Model.Aggregates;

import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType; // Importing the NotificationType enum to represent notification types
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType; // Importing the RecipientType enum to represent recipient types
import jakarta.persistence.*; // Importing JPA annotations for persistence
import lombok.Getter; // Lombok annotation for generating getters
import lombok.NoArgsConstructor; // Lombok annotation for generating the no-args constructor
import lombok.Setter; // Lombok annotation for generating setters

import java.time.LocalDateTime; // Importing LocalDateTime to store the timestamp when the notification was created

/**
 * The Notification class represents a notification in the system.
 * This is the aggregate root for notifications and it encapsulates all the details
 * regarding a specific notification, including its type, title, message, recipient, and creation time.
 */
@Entity
@Table(name = "notifications") // Specifies the table name in the database for this entity
@Getter // Lombok will automatically generate getter methods
@Setter // Lombok will automatically generate setter methods
@NoArgsConstructor // Lombok will generate a no-arguments constructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the primary key with auto-increment
    private Long id; // The unique identifier for the notification

    @Enumerated(EnumType.STRING) // Specifies that the enum type should be stored as a string in the database
    @Column(name = "notification_type", nullable = false) // Column for the type of the notification
    private NotificationType type; // The type of the notification (e.g., alert, reminder, etc.)

    @Column(name = "title", nullable = false, length = 200) // Column for the title of the notification
    private String title; // The title of the notification

    @Column(name = "message", nullable = false, length = 1000) // Column for the message body of the notification
    private String message; // The message content of the notification

    @Column(name = "created_at", nullable = false) // Column for the creation timestamp of the notification
    private LocalDateTime createdAt; // The date and time when the notification was created

    @Column(name = "recipient_id", nullable = false) // Column for the ID of the recipient
    private Long recipientId; // The ID of the recipient who will receive the notification

    @Enumerated(EnumType.STRING) // Specifies that the enum type should be stored as a string in the database
    @Column(name = "recipient_type", nullable = false) // Column for the recipient's type (e.g., volunteer, organization)
    private RecipientType recipientType; // The type of recipient (e.g., volunteer or organization)

    /**
     * Constructor to create a new Notification object.
     * This constructor initializes the Notification object with the provided details and sets the current time
     * as the creation time.
     *
     * @param type - The type of notification (e.g., alert, reminder, etc.)
     * @param title - The title of the notification
     * @param message - The message content of the notification
     * @param recipientId - The ID of the recipient
     * @param recipientType - The type of the recipient (e.g., volunteer or organization)
     */
    public Notification(NotificationType type, String title, String message, Long recipientId, RecipientType recipientType) {
        this.type = type; // Set the notification type
        this.title = title; // Set the notification title
        this.message = message; // Set the notification message
        this.createdAt = LocalDateTime.now(); // Set the current time as the creation timestamp
        this.recipientId = recipientId; // Set the recipient ID
        this.recipientType = recipientType; // Set the recipient type
    }
}
