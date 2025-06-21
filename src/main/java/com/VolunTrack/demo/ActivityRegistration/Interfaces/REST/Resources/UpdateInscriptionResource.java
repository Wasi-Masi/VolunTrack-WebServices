package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing InscriptionStatus enum
import java.time.LocalDate; // Importing LocalDate for the date of inscription

/**
 * The UpdateInscriptionResource class represents the data required to update an existing inscription.
 * This class serves as a **DTO (Data Transfer Object)** for receiving updated inscription data from the client.
 * It contains the necessary fields to modify an existing inscription in the system.
 */
public record UpdateInscriptionResource(
        Long voluntarioId, // The ID of the volunteer whose inscription is being updated
        InscriptionStatus estado, // The new status of the inscription (e.g., "pending", "confirmed")
        LocalDate fecha, // The new date of the inscription
        Long actividadId // The new activity ID that the volunteer is enrolled in
) {
}
