package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateInscriptionResource;

public class UpdateInscriptionCommandFromResourceAssembler {
    public static UpdateInscriptionCommand toCommandFromResource(Long inscriptionId, UpdateInscriptionResource resource) {
        return new UpdateInscriptionCommand(
                inscriptionId,
                resource.voluntarioId(),
                resource.estado(),
                resource.fecha(),
                resource.actividadId()
        );
    }
}