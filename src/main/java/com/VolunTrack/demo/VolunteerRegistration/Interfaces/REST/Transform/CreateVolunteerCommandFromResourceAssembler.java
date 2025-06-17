package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.CreateVolunteerResource;
import org.springframework.stereotype.Component;

/**
 * Assembler class to transform {@link CreateVolunteerResource} into {@link CreateVolunteerCommand}.
 * This class handles the mapping logic between the REST input and the application command.
 */
@Component
public class CreateVolunteerCommandFromResourceAssembler {

    /**
     * Converts a {@link CreateVolunteerResource} into a {@link CreateVolunteerCommand}.
     *
     * @param resource The CreateVolunteerResource to convert.
     * @return The corresponding CreateVolunteerCommand.
     */
    public CreateVolunteerCommand toCommandFromResource(CreateVolunteerResource resource) {
        return new CreateVolunteerCommand(
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.dateOfBirth(),
                resource.email(),
                resource.phoneNumber(),
                resource.address(),
                resource.organizationId(),
                resource.profession()
        );
    }
}