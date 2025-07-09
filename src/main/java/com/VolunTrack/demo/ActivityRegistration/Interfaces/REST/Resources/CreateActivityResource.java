package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The CreateActivityResource class is used to represent the data required to create a new activity.
 * This class serves as a **DTO (Data Transfer Object)** for receiving activity creation data from the client.
 * It contains the necessary details to create an activity in the system.
 */
public record CreateActivityResource(
        LocalDate fecha, // The date when the activity will take place
        LocalTime horaInicio, // The start time of the activity
        LocalTime horaFin, // The end time of the activity
        String titulo, // The title of the activity
        String descripcion, // A description of the activity
        String instrucciones, // Any special instructions for the activity
        String proposito, // The purpose of the activity
        int cupos, // The number of available spots for the activity
        String ubicacion, // The location where the activity will be held
        String estado, // The status of the activity (e.g., "active", "completed")
        int organizacionId // The ID of the organization that is organizing the activity
) {
}
