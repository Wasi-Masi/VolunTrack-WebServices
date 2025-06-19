package com.VolunTrack.demo.Notifications.Domain.Model.Enums;

public enum NotificationType {
    SIGNUP("¡Bienvenido a VolunTrack!", "Tu cuenta ha sido creada exitosamente. Ya puedes unirte a actividades."),
    LOGIN("Sesión iniciada", "Has iniciado sesión correctamente."),
    NEW_ACTIVITY("Actividad creada", "Has creado una nueva actividad de voluntariado. ¡Comparte para sumar voluntarios!"),
    JOINED_ACTIVITY("Inscripción confirmada", "Te has inscrito en una nueva actividad. ¡Gracias por tu compromiso!"),
    VOLUNTEER_JOINED("Nuevo voluntario", "Un voluntario se ha unido a una de tus actividades. Revisa los detalles."),
    CERTIFICATE_READY("Certificado disponible", "Tu certificado de participación ya está disponible para descargar."),
    REMINDER("Recordatorio de actividad", "Tienes una actividad programada pronto. No olvides revisar los detalles."),
    GENERIC("Notificación", "Tienes una nueva notificación en VolunTrack.");

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