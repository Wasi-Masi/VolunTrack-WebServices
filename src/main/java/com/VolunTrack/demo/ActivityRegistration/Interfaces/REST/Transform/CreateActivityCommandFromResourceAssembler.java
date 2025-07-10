package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
// Importar Component si se necesitara para otros métodos no-estáticos en esta misma clase
// import org.springframework.stereotype.Component;

/**
 * The CreateActivityCommandFromResourceAssembler class is responsible for converting a CreateActivityResource (which is a DTO)
 * into a CreateActivityCommand (which is used to represent the business logic for creating an activity).
 * This transformation ensures that the input from the client (in the form of a resource) is mapped into a command object
 * that can be processed by the command service layer.
 */
// Esta clase NO NECESITA @Component si todos sus métodos son estáticos.
public class CreateActivityCommandFromResourceAssembler {

    /**
     * Converts a CreateActivityResource to a CreateActivityCommand.
     * Este método es estático, por lo que se llama directamente desde la clase (ej. CreateActivityCommandFromResourceAssembler.toCommandFromResource(...)).
     */
    public static CreateActivityCommand toCommandFromResource(CreateActivityResource resource) {
        List<ActivityImage> activityImages = new ArrayList<>();
        if (resource.imagenes() != null) {
            activityImages = resource.imagenes().stream()
                    .map(ActivityImage::new) // Asumo que ActivityImage tiene un constructor que acepta un String (la URL)
                    .collect(Collectors.toList());
        }
        return new CreateActivityCommand(
                resource.fecha(),
                resource.horaInicio(),
                resource.horaFin(),
                resource.titulo(),
                resource.descripcion(),
                resource.instrucciones(),
                resource.proposito(),
                resource.cupos(),
                resource.ubicacion(),
                resource.estado(), // Asegúrate de que CreateActivityResource tiene un método estado()
                resource.organizacionId(), // Asumo resource.organizacionId(). Si es un POJO, sería resource.getOrganizacionId().
                activityImages
        );
    }
}