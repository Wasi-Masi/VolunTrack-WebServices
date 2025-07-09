package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand; // Importing CreateInscriptionCommand to convert resource to command
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateInscriptionResource; // Importing CreateInscriptionResource which will be converted to command

/**
 * The CreateInscriptionCommandFromResourceAssembler class is responsible for converting a CreateInscriptionResource (which is a DTO) 
 * into a CreateInscriptionCommand (which represents the business logic for creating an inscription).
 * This transformation ensures that the input from the client (in the form of a resource) is mapped into a command object 
 * that can be processed by the command service layer.
 */
public class CreateInscriptionCommandFromResourceAssembler {

    /**
     * Converts a CreateInscriptionResource to a CreateInscriptionCommand.
     * This method takes the resource (received from the client) and converts it to a command 
     * that the application service will handle to create a new inscription.
     *
     * @param resource - The CreateInscriptionResource containing the inscription details from the client.
     * @return A CreateInscriptionCommand that can be handled by the InscriptionCommandService.
     */
    public static CreateInscriptionCommand toCommandFromResource(CreateInscriptionResource resource) {
        // Mapping the fields from the resource to the command
        return new CreateInscriptionCommand(
                resource.voluntarioId(), // The ID of the volunteer who is being enrolled
                resource.estado(), // The status of the inscription (e.g., "pending", "confirmed")
                resource.fecha(), // The date when the inscription is created
                resource.actividadId() // The ID of the activity the volunteer is enrolling in
        );
    }
}
