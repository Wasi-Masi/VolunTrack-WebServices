package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteer;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.OrgVolunteerResource;
import org.springframework.stereotype.Component;

/**
 * Assembler class to transform {@link OrgVolunteer} entity into {@link OrgVolunteerResource}.
 * This class handles the mapping logic from the domain entity (association) to the REST output DTO.
 */
@Component
public class OrgVolunteerResourceFromEntityAssembler {

    /**
     * Converts an {@link OrgVolunteer} entity into an {@link OrgVolunteerResource}.
     *
     * @param entity The OrgVolunteer entity to convert.
     * @return The corresponding OrgVolunteerResource.
     */
    public OrgVolunteerResource toResourceFromEntity(OrgVolunteer entity) {
        return new OrgVolunteerResource(
                entity.getOrganization().getId(),
                entity.getVolunteer().getId()
        );
    }
}