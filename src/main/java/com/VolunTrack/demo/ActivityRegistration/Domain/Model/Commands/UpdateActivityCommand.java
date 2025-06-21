package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a command to update an existing activity.
 * This command encapsulates all the necessary data to update an existing activity in the system.
 * In a CQRS (Command Query Responsibility Segregation) architecture,
 * this command is part of the write model, used to trigger the update of an activity.
 */
public record UpdateActivityCommand(
        /// <summary>
        /// The unique ID of the activity to be updated.
        /// </summary>
        Long actividadId, // Unique identifier for the activity to be updated.

        /// <summary>
        /// The new date of the activity.
        /// </summary>
        LocalDate fecha, // The new date of the activity.

        /// <summary>
        /// The new start time of the activity.
        /// </summary>
        LocalTime horainicio, // The new start time of the activity.

        /// <summary>
        /// The new end time of the activity.
        /// </summary>
        LocalTime horaFin, // The new end time of the activity.

        /// <summary>
        /// The new title of the activity.
        /// </summary>
        String titulo, // The new title of the activity.

        /// <summary>
        /// The new description of the activity.
        /// </summary>
        String descripcion, // The new description of the activity.

        /// <summary>
        /// New instructions for the activity.
        /// </summary>
        String instrucciones, // The new instructions for the activity.

        /// <summary>
        /// The new purpose of the activity.
        /// </summary>
        String proposito, // The new purpose of the activity.

        /// <summary>
        /// The new total number of available slots for the activity.
        /// </summary>
        int cupos, // The new number of available slots for the activity.

        /// <summary>
        /// The new location of the activity.
        /// </summary>
        String ubicacion, // The new location of the activity.

        /// <summary>
        /// The new status of the activity (e.g., "Active", "Completed").
        /// </summary>
        String estado, // The new status of the activity.

        /// <summary>
        /// The new ID of the organization hosting the activity.
        /// </summary>
        int organizacionId // The new organization ID for the activity.
) {
}
