package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource;

public class ActivityResourceFromEntityAssembler {
    public static ActivityResource toResourceFromEntity(Activity entity) {
        return new ActivityResource(
                entity.getActividad_id(),
                entity.getFecha(),
                entity.getHorainicio(),
                entity.getHoraFin(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getInstrucciones(),
                entity.getProposito(),
                entity.getCupos(),
                entity.getUbicacion(),
                entity.getEstado(),
                entity.getOrganizacion_id()
        );
    }
}