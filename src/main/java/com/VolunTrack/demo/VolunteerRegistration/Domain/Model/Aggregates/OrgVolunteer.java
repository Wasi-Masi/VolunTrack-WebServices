package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents the association between an {@link Organization} and a {@link Volunteer}.
 * This is an entity for the many-to-many relationship table 'organization_volunteer'.
 * It uses a composite primary key defined by {@link OrgVolunteerId}.
 */
@Entity
@Table(name = "organization_volunteer")
@Getter
@Setter
@NoArgsConstructor
public class OrgVolunteer {

    /**
     * The composite primary key for the OrgVolunteer entity.
     * This field embeds the OrgVolunteerId class, containing organizationId and volunteerId.
     */
    @EmbeddedId
    private OrgVolunteerId id;

    /**
     * The {@link Organization} associated with this entry.
     * This is the 'many' side of a Many-to-One relationship.
     * @MapsId("organizationId") indicates that the 'organizationId' part of the composite key
     * is mapped from the ID of the Organization entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id", foreignKey = @ForeignKey(name = "FK_org_volunteer_organization")) // Nombre de la columna FK
    private Organization organization;

    /**
     * The {@link Volunteer} associated with this entry.
     * This is the 'many' side of a Many-to-One relationship.
     * @MapsId("volunteerId") indicates that the 'volunteerId' part of the composite key
     * is mapped from the ID of the Volunteer entity.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Volunteer
    @MapsId("volunteerId") // Mapea la parte 'volunteerId' de la clave compuesta
    @JoinColumn(name = "volunteer_id", foreignKey = @ForeignKey(name = "FK_org_volunteer_volunteer")) // Nombre de la columna FK
    private Volunteer volunteer;

    /**
     * Custom constructor for creating a new OrgVolunteer association.
     * It initializes the composite ID and sets the associated Organization and Volunteer.
     *
     * @param organization The Organization to associate.
     * @param volunteer The Volunteer to associate.
     */
    public OrgVolunteer(Organization organization, Volunteer volunteer) {
        this.organization = organization;
        this.volunteer = volunteer;

        this.id = new OrgVolunteerId(organization.getId(), volunteer.getId());
    }
}