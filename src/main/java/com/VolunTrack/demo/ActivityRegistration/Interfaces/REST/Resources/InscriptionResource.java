package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing the InscriptionStatus enum
import java.time.LocalDate; // Importing LocalDate for the date of inscription

/**
 * The InscriptionResource class represents an inscription (enrollment) resource.
 * This class is used to represent the data of an inscription when returned from the API.
 * It contains the details of a volunteer's enrollment in an activity.
 */
public record InscriptionResource(
        Long inscription_id, // The unique ID of the inscription
        Long voluntarioId, // The ID of the volunteer who is enrolled
        InscriptionStatus estado, // The status of the inscription (e.g., "pending", "confirmed")
        LocalDate fecha, // The date the inscription was created
        Long actividadId // The ID of the activity that the volunteer is enrolled in
) {
}
