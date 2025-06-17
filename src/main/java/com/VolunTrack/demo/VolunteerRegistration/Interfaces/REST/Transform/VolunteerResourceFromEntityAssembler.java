package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.VolunteerResource;
import org.springframework.stereotype.Component;

/**
 * Assembler class to transform {@link Volunteer} entity into {@link VolunteerResource}.
 * This class handles the mapping logic from the domain entity to the REST output DTO.
 */
@Component
public class VolunteerResourceFromEntityAssembler {

    /**
     * Converts a {@link Volunteer} entity into a {@link VolunteerResource}.
     *
     * @param entity The Volunteer entity to convert.
     * @return The corresponding VolunteerResource.
     */
    public VolunteerResource toResourceFromEntity(Volunteer entity) {
        return new VolunteerResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDni(),
                entity.getDateOfBirth(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getRegistrationDate(),
                entity.getStatus().name(),
                entity.getAddress(),
                entity.getProfession()
        );
    }
}