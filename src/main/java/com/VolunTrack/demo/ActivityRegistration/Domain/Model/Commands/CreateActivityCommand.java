package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Represents a command to create a new activity.
 * This command encapsulates all the necessary data to create an activity.
 * In a CQRS (Command Query Responsibility Segregation) architecture,
 * this command is part of the write model, as it is used to trigger the creation of a new activity.
 */
public record CreateActivityCommand(
        /// <summary>
        /// The date of the activity.
        /// </summary>
        LocalDate fecha,

        /// <summary>
        /// The start time of the activity.
        /// </summary>
        LocalTime horaInicio,

        /// <summary>
        /// The end time of the activity.
        /// </summary>
        LocalTime horaFin,

        /// <summary>
        /// The title of the activity.
        /// </summary>
        String titulo,

        /// <summary>
        /// A description of the activity.
        /// </summary>
        String descripcion,

        /// <summary>
        /// Any specific instructions for the activity.
        /// </summary>
        String instrucciones,

        /// <summary>
        /// The purpose of the activity.
        /// </summary>
        String proposito,

        /// <summary>
        /// The total number of available slots for the activity.
        /// </summary>
        int cupos,

        /// <summary>
        /// The location where the activity will take place.
        /// </summary>
        String ubicacion,

        /// <summary>
        /// The status of the activity (e.g., Active, Pending, etc.).
        /// </summary>
        String estado,

        /// <summary>
        /// The ID of the organization hosting the activity.
        /// </summary>
        int organizacionId,

        /// <summary>
        /// A list of image URLs associated with the activity.
        /// </summary>
        List<ActivityImage> imagenes
) {
}
