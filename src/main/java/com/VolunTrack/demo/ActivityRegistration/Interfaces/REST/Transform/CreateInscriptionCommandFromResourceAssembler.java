package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateInscriptionResource;

public class CreateInscriptionCommandFromResourceAssembler {
    public static CreateInscriptionCommand toCommandFromResource(CreateInscriptionResource resource) {
        return new CreateInscriptionCommand(
                resource.voluntarioId(),
                resource.estado(),
                resource.fecha(),
                resource.actividadId()
        );
    }
}