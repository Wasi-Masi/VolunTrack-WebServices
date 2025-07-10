package com.VolunTrack.demo.Notifications.Domain.Model.Enums;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public enum NotificationType {

    SIGNUP("notification.signup.title", "notification.signup.message"),
    LOGIN("notification.login.title", "notification.login.message"),
    NEW_ACTIVITY("notification.new_activity.title", "notification.new_activity.message"),
    JOINED_ACTIVITY("notification.joined_activity.title", "notification.joined_activity.message"),
    VOLUNTEER_JOINED("notification.volunteer_joined.title", "notification.volunteer_joined.message"),
    CERTIFICATE_READY("notification.certificate_ready.title", "notification.certificate_ready.message"),
    REMINDER("notification.reminder.title", "notification.reminder.message"),
    GENERIC("notification.generic.title", "notification.generic.message");

    private final String titleKey;
    private final String messageKey;

    NotificationType(String titleKey, String messageKey) {
        this.titleKey = titleKey;
        this.messageKey = messageKey;
    }

    public String getTitle(MessageSource messageSource) {
        return messageSource.getMessage(titleKey, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(MessageSource messageSource) {
        return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }
}
