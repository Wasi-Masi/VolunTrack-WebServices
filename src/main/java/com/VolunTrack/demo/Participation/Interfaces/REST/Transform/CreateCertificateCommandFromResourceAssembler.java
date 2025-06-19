package com.VolunTrack.demo.Participation.Interfaces.REST.Transform;

import com.VolunTrack.demo.Participation.Domain.Model.Commands.CreateCertificateCommand;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateCertificateResource;

/**
 * Assembler to transform a {@link CreateCertificateResource} into a {@link CreateCertificateCommand}.
 */
public class CreateCertificateCommandFromResourceAssembler {

    public static CreateCertificateCommand toCommandFromResource(CreateCertificateResource resource) {
        return new CreateCertificateCommand(
                resource.participationId(),
                resource.description()
        );
    }
}