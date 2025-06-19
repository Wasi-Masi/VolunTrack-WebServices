package com.VolunTrack.demo.Participation.Interfaces.REST.Transform;

import com.VolunTrack.demo.Participation.Domain.Model.Commands.CreateParticipationCommand;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateParticipationResource;

/**
 * Assembler to transform a {@link CreateParticipationResource} into a {@link CreateParticipationCommand}.
 */
public class CreateParticipationCommandFromResourceAssembler {

    public static CreateParticipationCommand toCommandFromResource(CreateParticipationResource resource) {
        return new CreateParticipationCommand(
                resource.volunteerId(),
                resource.activityId(),
                resource.initialStatus()
        );
    }
}