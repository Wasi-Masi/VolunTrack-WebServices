package com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link OrgVolunteer} entity.
 * This class encapsulates the IDs of the Organization and Volunteer to form a unique key.
 * It must implement Serializable, and correctly override equals() and hashCode().
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgVolunteerId implements Serializable {

    /**
     * The ID of the associated Organization.
     */
    private Long organizationId;

    /**
     * The ID of the associated Volunteer.
     */
    private Long volunteerId;

    /**
     * Overrides the equals method to compare two OrgVolunteerId objects based on their
     * organizationId and volunteerId. Required for composite keys.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgVolunteerId that = (OrgVolunteerId) o;
        return Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(volunteerId, that.volunteerId);
    }

    /**
     * Overrides the hashCode method, essential for composite keys.
     * Generates a hash code based on organizationId and volunteerId.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(organizationId, volunteerId);
    }
}