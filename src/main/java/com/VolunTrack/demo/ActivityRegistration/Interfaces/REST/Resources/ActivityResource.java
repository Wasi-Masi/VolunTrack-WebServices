package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The ActivityResource class is used to represent an activity in the API response.
 * This class is a **DTO (Data Transfer Object)** that is used to send activity data to the client.
 * It contains the details of the activity such as title, description, date, time, and more.
 */
public record ActivityResource(
        Long actividad_id, // The unique identifier of the activity
        LocalDate fecha, // The date when the activity takes place
        LocalTime horaInicio, // The start time of the activity
        LocalTime horaFin, // The end time of the activity
        String titulo, // The title of the activity
        String descripcion, // A description of the activity
        String instrucciones, // Any instructions related to the activity
        String proposito, // The purpose of the activity
        int cupos, // The number of available spots for the activity
        String ubicacion, // The location where the activity takes place
        String estado, // The current status of the activity (e.g., "active", "completed")
        int organizacionId, // The ID of the organization that is organizing the activity
        List<String> imagenes // The list of images urls for the activity
) {
}
