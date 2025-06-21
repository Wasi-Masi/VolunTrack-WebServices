package com.VolunTrack.demo.Notifications.Domain.Model.Enums;

public enum NotificationType {
    SIGNUP("Welcome to VolunTrack!", "Your account has been successfully created. You can now join activities."),
    LOGIN("Session started", "You have successfully logged in."),
    NEW_ACTIVITY("Activity created", "You have created a new volunteer activity. Share to add volunteers!"),
    JOINED_ACTIVITY("Registration confirmed", "You have signed up for a new activity. Thank you for your commitment!"),
    VOLUNTEER_JOINED("New volunteer", "A volunteer has joined one of your activities. Review the details."),
    CERTIFICATE_READY("Certificate available", "Your participation certificate is now available for download."),
    REMINDER("Activity reminder", "You have an activity scheduled soon. Don't forget to review the details."),
    GENERIC("Notification", "You have a new notification in VolunTrack.");

    private final String defaultTitle;
    private final String defaultMessage;

    NotificationType(String defaultTitle, String defaultMessage) {
        this.defaultTitle = defaultTitle;
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}