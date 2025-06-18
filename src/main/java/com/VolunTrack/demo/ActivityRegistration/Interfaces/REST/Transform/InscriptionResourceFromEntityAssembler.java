package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.InscriptionResource;

public class InscriptionResourceFromEntityAssembler {
    public static InscriptionResource toResourceFromEntity(Inscription entity) {
        return new InscriptionResource(
                entity.getInscription_id(),
                entity.getVoluntarioId(),
                entity.getEstado(),
                entity.getFecha(),
                entity.getActividadId()
        );
    }
}