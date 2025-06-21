package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing InscriptionStatus enum for status handling

import java.time.LocalDate;

/**
 * Represents a command to update an existing inscription.
 * This command encapsulates the necessary data to update an inscription in the system.
 * In a CQRS (Command Query Responsibility Segregation) architecture,
 * this command is part of the write model, used to trigger the update of an inscription.
 */
public record UpdateInscriptionCommand(
        /// <summary>
        /// The unique ID of the inscription to be updated.
        /// </summary>
        Long inscriptionId, // Unique identifier for the inscription that needs to be updated.

        /// <summary>
        /// The unique ID of the volunteer associated with the inscription.
        /// </summary>
        Long voluntarioId, // The ID of the volunteer whose inscription is being updated.

        /// <summary>
        /// The new status of the inscription (e.g., Pending, Confirmed, Canceled).
        /// </summary>
        InscriptionStatus estado, // Enum representing the new status of the inscription.

        /// <summary>
        /// The new date of the inscription.
        /// </summary>
        LocalDate fecha, // The new date when the inscription was made.

        /// <summary>
        /// The new ID of the activity the volunteer is registered for.
        /// </summary>
        Long actividadId // The new ID of the activity that the volunteer is registered for.
) {
}
