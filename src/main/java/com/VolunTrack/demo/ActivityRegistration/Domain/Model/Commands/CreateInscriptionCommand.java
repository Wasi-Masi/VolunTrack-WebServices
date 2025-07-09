package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing the InscriptionStatus enum for status handling

import java.time.LocalDate;

/**
 * Represents a command to create a new inscription for a volunteer in an activity.
 * This command encapsulates all the necessary data to create a new inscription.
 * It is part of the write model in a CQRS (Command Query Responsibility Segregation) architecture.
 */
public record CreateInscriptionCommand(
        /// <summary>
        /// The unique ID of the volunteer who is registering for the activity.
        /// </summary>
        Long voluntarioId,

        /// <summary>
        /// The status of the inscription (e.g., Pending, Confirmed, Canceled).
        /// </summary>
        InscriptionStatus estado, // Enum for the inscription status (Pending, Confirmed, etc.)

        /// <summary>
        /// The date when the inscription is made.
        /// </summary>
        LocalDate fecha, // Date of inscription (when the volunteer signed up for the activity)

        /// <summary>
        /// The unique ID of the activity the volunteer is registering for.
        /// </summary>
        Long actividadId // ID of the activity the volunteer is signing up for
) {
}
