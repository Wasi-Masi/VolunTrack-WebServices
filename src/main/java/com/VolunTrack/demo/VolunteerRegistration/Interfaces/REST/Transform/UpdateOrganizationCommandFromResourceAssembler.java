package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.UpdateOrganizationResource;

public class UpdateOrganizationCommandFromResourceAssembler {
    public static UpdateOrganizationCommand toCommandFromResource(Long organizationId, UpdateOrganizationResource resource) {
        return new UpdateOrganizationCommand(
                organizationId,
                resource.name(),
                resource.description(),
                resource.email(),
                resource.plan()
        );
    }
}