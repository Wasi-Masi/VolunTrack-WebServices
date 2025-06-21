package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand; // Importing UpdateInscriptionCommand to convert resource to command
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateInscriptionResource; // Importing UpdateInscriptionResource which will be converted to command

/**
 * The UpdateInscriptionCommandFromResourceAssembler class is responsible for converting an UpdateInscriptionResource (which is a DTO)
 * into an UpdateInscriptionCommand (which represents the business logic for updating an inscription).
 * This transformation ensures that the input data from the client (in the form of a resource) is mapped into a command object 
 * that can be processed by the command service layer.
 */
public class UpdateInscriptionCommandFromResourceAssembler {

    /**
     * Converts an UpdateInscriptionResource to an UpdateInscriptionCommand.
     * This method takes the resource (received from the client) and converts it to a command 
     * that the application service will handle to update an existing inscription.
     *
     * @param inscriptionId - The ID of the inscription to be updated.
     * @param resource - The UpdateInscriptionResource containing the updated inscription details from the client.
     * @return An UpdateInscriptionCommand that can be handled by the InscriptionCommandService to update the inscription.
     */
    public static UpdateInscriptionCommand toCommandFromResource(Long inscriptionId, UpdateInscriptionResource resource) {
        // Mapping the fields from the resource to the command
        return new UpdateInscriptionCommand(
                inscriptionId, // The unique ID of the inscription to update
                resource.voluntarioId(), // The ID of the volunteer who is enrolled
                resource.estado(), // The new status of the inscription (e.g., "pending", "confirmed")
                resource.fecha(), // The new date of the inscription
                resource.actividadId() // The ID of the activity the volunteer is enrolled in
        );
    }
}
