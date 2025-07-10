package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ActivityResourceFromEntityAssembler {

    public ActivityResource toResourceFromEntity(Activity entity) {
        return new ActivityResource(
                entity.getActividad_id(),
                entity.getFecha(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getInstrucciones(),
                entity.getProposito(),
                entity.getCupos(),
                entity.getCupos() - entity.getInscripcionesActuales(),
                entity.getUbicacion(),
                entity.getEstado(),
                entity.getOrganizacion_id(),
                entity.getImagenes().stream()
                        // CORRECCIÓN AQUÍ: Usar getImageUrl() generado por Lombok
                        .map(image -> image.getImageUrl())
                        .collect(Collectors.toList())
        );
    }
}