package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand; // Importing CreateActivityCommand to convert the resource to command
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource; // Importing CreateActivityResource which will be converted to command

/**
 * The CreateActivityCommandFromResourceAssembler class is responsible for converting a CreateActivityResource (which is a DTO) 
 * into a CreateActivityCommand (which is used to represent the business logic for creating an activity).
 * This transformation ensures that the input from the client (in the form of a resource) is mapped into a command object 
 * that can be processed by the command service layer.
 */
public class CreateActivityCommandFromResourceAssembler {

    /**
     * Converts a CreateActivityResource to a CreateActivityCommand.
     * This method takes the resource (received from the client) and converts it to a command 
     * that the application service will handle to create a new activity.
     *
     * @param resource - The CreateActivityResource containing the activity details from the client.
     * @return A CreateActivityCommand that can be handled by the ActivityCommandService.
     */
    public static CreateActivityCommand toCommandFromResource(CreateActivityResource resource) {
        // Mapping the fields from the resource to the command
        return new CreateActivityCommand(
                resource.fecha(), // The date of the activity
                resource.horaInicio(), // The start time of the activity
                resource.horaFin(), // The end time of the activity
                resource.titulo(), // The title of the activity
                resource.descripcion(), // The description of the activity
                resource.instrucciones(), // Instructions for the activity
                resource.proposito(), // The purpose of the activity
                resource.cupos(), // Number of available slots for the activity
                resource.ubicacion(), // The location of the activity
                resource.estado(), // The current status of the activity
                resource.organizacionId() // The organization responsible for the activity
        );
    }
}
