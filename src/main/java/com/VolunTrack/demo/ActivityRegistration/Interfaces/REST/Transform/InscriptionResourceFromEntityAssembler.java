package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription; // Importing the Inscription aggregate
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.InscriptionResource; // Importing the InscriptionResource for returning the resource

/**
 * The InscriptionResourceFromEntityAssembler class is responsible for converting an Inscription entity
 * into an InscriptionResource, which is a DTO (Data Transfer Object) used for API responses.
 * This transformation ensures that the domain model (Inscription entity) is properly formatted for client consumption.
 */
public class InscriptionResourceFromEntityAssembler {

    /**
     * Converts an Inscription entity to an InscriptionResource.
     * This method takes the Inscription entity (which contains domain logic) and converts it into a simpler DTO 
     * (Data Transfer Object) suitable for API responses.
     *
     * @param entity - The Inscription entity to be converted to a resource.
     * @return An InscriptionResource containing the data of the Inscription entity, formatted for API responses.
     */
    public static InscriptionResource toResourceFromEntity(Inscription entity) {
        // Mapping fields from the entity to the resource
        return new InscriptionResource(
                entity.getInscription_id(), // The unique identifier of the inscription
                entity.getVoluntarioId(), // The ID of the volunteer who is enrolled
                entity.getEstado(), // The status of the inscription (e.g., "pending", "confirmed")
                entity.getFecha(), // The date the inscription was created
                entity.getActividadId() // The ID of the activity the volunteer is enrolled in
        );
    }
}
