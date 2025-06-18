package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource;

public class UpdateActivityCommandFromResourceAssembler {
    public static UpdateActivityCommand toCommandFromResource(Long actividadId, UpdateActivityResource resource) {
        return new UpdateActivityCommand(
                actividadId,
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