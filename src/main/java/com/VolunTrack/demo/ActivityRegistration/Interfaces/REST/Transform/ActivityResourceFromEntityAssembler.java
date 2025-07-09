package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity; // Importing the Activity aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage; // Importing the ActivityImage value object
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource; // Importing ActivityResource to return the resource

import java.util.List;
import java.util.stream.Collectors;

/**
 * The ActivityResourceFromEntityAssembler class is responsible for converting an Activity entity
 * into an ActivityResource, which is a DTO (Data Transfer Object) used for API responses.
 * This transformation is necessary to separate the domain model from the presentation layer.
 */
public class ActivityResourceFromEntityAssembler {

    /**
     * Converts an Activity entity to an ActivityResource.
     * This method takes the Activity entity (which contains domain logic) and converts it to a simpler DTO 
     * (Data Transfer Object) for API responses.
     * 
     * @param entity - The Activity entity that needs to be converted.
     * @return An ActivityResource containing the data of the Activity entity, suitable for use in API responses.
     */
    public static ActivityResource toResourceFromEntity(Activity entity) {
        // Mapping the fields from the entity to the resource
        List<String> activityImages = entity.getImagenes()
                .stream()
                .map(ActivityImage::getImageUrl)
                .collect(Collectors.toList());
        return new ActivityResource(
                entity.getActividad_id(), // The unique identifier of the activity
                entity.getFecha(), // The date of the activity
                entity.getHoraInicio(), // The start time of the activity
                entity.getHoraFin(), // The end time of the activity
                entity.getTitulo(), // The title of the activity
                entity.getDescripcion(), // A description of the activity
                entity.getInstrucciones(), // Any instructions related to the activity
                entity.getProposito(), // The purpose of the activity
                entity.getCupos(), // The number of available spots for the activity
                entity.getUbicacion(), // The location of the activity
                entity.getEstado(), // The current status of the activity
                entity.getOrganizacion_id(), // The ID of the organization responsible for the activity
                activityImages // The activity images for the activity
        );
    }
}
