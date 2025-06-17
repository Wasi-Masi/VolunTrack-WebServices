package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.OrganizationResource;
import org.springframework.stereotype.Component;

/**
 * Assembler class to transform {@link Organization} entity into {@link OrganizationResource}.
 * This class handles the mapping logic from the domain entity to the REST output DTO.
 */
@Component
public class OrganizationResourceFromEntityAssembler {

    /**
     * Converts an {@link Organization} entity into an {@link OrganizationResource}.
     *
     * @param entity The Organization entity to convert.
     * @return The corresponding OrganizationResource.
     */
    public OrganizationResource toResourceFromEntity(Organization entity) {
        return new OrganizationResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getEmail(),
                entity.getPlan()
        );
    }
}