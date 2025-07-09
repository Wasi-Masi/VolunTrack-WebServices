package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand; // Importing UpdateActivityCommand to convert the resource to command
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage; // Importing the ActivityImage value object
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource; // Importing UpdateActivityResource which will be converted to command

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The UpdateActivityCommandFromResourceAssembler class is responsible for converting an UpdateActivityResource (which is a DTO)
 * into an UpdateActivityCommand (which represents the business logic for updating an activity).
 * This transformation ensures that the input data from the client (in the form of a resource) is mapped into a command object 
 * that can be processed by the command service layer.
 */
public class UpdateActivityCommandFromResourceAssembler {

    /**
     * Converts an UpdateActivityResource to an UpdateActivityCommand.
     * This method takes the resource (received from the client) and converts it to a command 
     * that the application service will handle to update an existing activity.
     *
     * @param actividadId - The ID of the activity to be updated.
     * @param resource - The UpdateActivityResource containing the updated activity details from the client.
     * @return An UpdateActivityCommand that can be handled by the ActivityCommandService to update the activity.
     */
    public static UpdateActivityCommand toCommandFromResource(Long actividadId, UpdateActivityResource resource) {
        // Mapping the fields from the resource to the command
        List<ActivityImage> activityImages = new ArrayList<>();
        if (resource.imagenes() != null) {
            activityImages = resource.imagenes().stream()
                    .map(ActivityImage::new)
                    .collect(Collectors.toList());
        }
        return new UpdateActivityCommand(
                actividadId, // The unique ID of the activity to update
                resource.fecha(), // The new date of the activity
                resource.horaInicio(), // The new start time of the activity
                resource.horaFin(), // The new end time of the activity
                resource.titulo(), // The new title of the activity
                resource.descripcion(), // The new description of the activity
                resource.instrucciones(), // The new instructions for the activity
                resource.proposito(), // The new purpose of the activity
                resource.cupos(), // The new number of available spots for the activity
                resource.ubicacion(), // The new location where the activity will be held
                resource.estado(), // The new status of the activity (e.g., "active", "completed")
                resource.organizacionId(), // The new organization ID responsible for the activity
                activityImages // The activity images for the activity
        );
    }
}
