package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing the InscriptionStatus enum
import java.time.LocalDate; // Importing LocalDate for the date of inscription

/**
 * The CreateInscriptionResource class represents the data required to create a new inscription (enrollment) in the system.
 * This class serves as a **DTO (Data Transfer Object)** for receiving inscription creation data from the client.
 * It contains the necessary details for enrolling a volunteer in an activity.
 */
public record CreateInscriptionResource(
        Long voluntarioId, // The ID of the volunteer who is being enrolled
        InscriptionStatus estado, // The status of the inscription (e.g., "pending", "confirmed")
        LocalDate fecha, // The date the inscription is being created
        Long actividadId // The ID of the activity to which the volunteer is enrolling
) {
}
