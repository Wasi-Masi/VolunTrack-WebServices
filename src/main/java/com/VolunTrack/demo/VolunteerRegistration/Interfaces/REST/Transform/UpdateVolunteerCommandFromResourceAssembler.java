package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.UpdateVolunteerResource;
import org.springframework.stereotype.Component;

/**
 * Assembler class to transform {@link UpdateVolunteerResource} into {@link UpdateVolunteerCommand}.
 * This class handles the mapping logic between the REST input and the application command.
 */
@Component
public class UpdateVolunteerCommandFromResourceAssembler {

    /**
     * Converts a {@link UpdateVolunteerResource} into an {@link UpdateVolunteerCommand}.
     * Note: The volunteerId needs to be provided separately as it's part of the path variable in REST.
     *
     * @param volunteerId The ID of the volunteer to update.
     * @param resource The UpdateVolunteerResource to convert.
     * @return The corresponding UpdateVolunteerCommand.
     */
    public UpdateVolunteerCommand toCommandFromResource(Long volunteerId, UpdateVolunteerResource resource) {
        return new UpdateVolunteerCommand(
                volunteerId,
                resource.firstName(),
                resource.lastName(),
                resource.dni(),
                resource.dateOfBirth(),
                resource.email(),
                resource.phoneNumber(),
                resource.address(),
                resource.profession(),
                resource.status()
        );
    }
}