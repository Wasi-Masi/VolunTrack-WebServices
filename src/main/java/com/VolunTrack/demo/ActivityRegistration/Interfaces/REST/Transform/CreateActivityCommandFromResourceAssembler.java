package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource;

public class CreateActivityCommandFromResourceAssembler {
    public static CreateActivityCommand toCommandFromResource(CreateActivityResource resource) {
        return new CreateActivityCommand(
                resource.fecha(),
                resource.horainicio(),
                resource.horaFin(),
                resource.titulo(),
                resource.descripcion(),
                resource.instrucciones(),
                resource.proposito(),
                resource.cupos(),
                resource.ubicacion(),
                resource.estado(),
                resource.organizacionId()
        );
    }
}