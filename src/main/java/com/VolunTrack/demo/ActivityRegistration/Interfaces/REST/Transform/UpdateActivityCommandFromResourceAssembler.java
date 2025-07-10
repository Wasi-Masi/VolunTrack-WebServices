package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage;

@Component
public class UpdateActivityCommandFromResourceAssembler {

    public UpdateActivityCommand toCommandFromResource(Long activityId, UpdateActivityResource resource) {
        List<ActivityImage> activityImages = new ArrayList<>();
        if (resource.imagenes() != null) {
            activityImages = resource.imagenes().stream()
                    .map(ActivityImage::new)
                    .collect(Collectors.toList());
        }
        return new UpdateActivityCommand(
                activityId,
                resource.fecha(),
                resource.horaInicio(),
                resource.horaFin(),
                resource.titulo(),
                resource.descripcion(),
                resource.instrucciones(),
                resource.proposito(),
                resource.cupos(),
                // ELIMINAR ESTA LÍNEA: resource.inscripcionesActuales(), // Este argumento ya no se pasa
                resource.ubicacion(),
                resource.estado(),
                resource.organizacionId(),
                activityImages
        );
    }
}