package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The UpdateActivityResource class represents the data required to update an existing activity.
 * This class serves as a **DTO (Data Transfer Object)** for receiving updated activity data from the client.
 * It contains the necessary fields to modify an existing activity's information in the system.
 */
public record UpdateActivityResource(
        LocalDate fecha, // The new date when the activity will take place
        LocalTime horaInicio, // The new start time of the activity
        LocalTime horaFin, // The new end time of the activity
        String titulo, // The new title of the activity
        String descripcion, // The new description of the activity
        String instrucciones, // Any updated instructions for the activity
        String proposito, // The new purpose of the activity
        int cupos, // The new number of available spots for the activity
        String ubicacion, // The new location where the activity will be held
        String estado, // The new status of the activity (e.g., "active", "completed")
        int organizacionId // The new organization ID responsible for the activity
) {
}
