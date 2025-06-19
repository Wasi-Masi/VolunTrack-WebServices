package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.CreateOrganizationResource;

public class CreateOrganizationCommandFromResourceAssembler {
    public static CreateOrganizationCommand toCommandFromResource(CreateOrganizationResource resource) {
        return new CreateOrganizationCommand(
                resource.name(),
                resource.description(),
                resource.email(),
                resource.plan()
        );
    }
}