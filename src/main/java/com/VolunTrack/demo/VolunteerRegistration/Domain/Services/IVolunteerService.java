package com.VolunTrack.demo.VolunteerRegistration.Domain.Services;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.VolunteerStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Domain Service interface for Volunteer-related business operations.
 * This service handles logic that might involve multiple aggregates or complex domain rules
 * that don't naturally fit within the Volunteer entity itself.
 */
public interface IVolunteerService {

    /**
     * Creates a new volunteer and associates them with a specified organization.
     *
     * @param firstName The first name of the volunteer.
     * @param lastName The last name of the volunteer.
     * @param dni The DNI of the volunteer.
     * @param dateOfBirth The date of birth of the volunteer.
     * @param email The email address of the volunteer.
     * @param phoneNumber The phone number of the volunteer.
     * @param address The address of the volunteer.
     * @param organizationId The ID of the organization to associate the volunteer with.
     * @param profession The profession of the volunteer (can be null).
     * @return An Optional containing the created Volunteer if successful, or empty if validation fails (e.g., DNI/email already exists, or organization not found).
     */
    Optional<Volunteer> createVolunteer(String firstName, String lastName, String dni, LocalDate dateOfBirth, String email, String phoneNumber, String address, Long organizationId, String profession);

    /**
     * Updates an existing volunteer's information.
     *
     * @param volunteerId The ID of the volunteer to update.
     * @param firstName The new first name (can be null if not updated).
     * @param lastName The new last name (can be null if not updated).
     * @param dni The new DNI (can be null if not updated).
     * @param dateOfBirth The new date of birth (can be null if not updated).
     * @param email The new email address (can be null if not updated).
     * @param phoneNumber The new phone number (can be null if not updated).
     * @param address The new address (can be null if not updated).
     * @param profession The new profession (can be null if not updated).
     * @param status The new status (can be null if not updated).
     * @return An Optional containing the updated Volunteer if found and updated, or empty if not found or validation fails.
     */
    Optional<Volunteer> updateVolunteer(Long volunteerId, String firstName, String lastName, String dni, LocalDate dateOfBirth, String email, String phoneNumber, String address, String profession, VolunteerStatus status);

    /**
     * Deletes a volunteer by their ID.
     *
     * @param volunteerId The ID of the volunteer to delete.
     * @return True if the volunteer was successfully deleted, false otherwise.
     */
    boolean deleteVolunteer(Long volunteerId);

    /**
     * Retrieves a volunteer by their ID.
     *
     * @param volunteerId The ID of the volunteer.
     * @return An Optional containing the Volunteer if found, or empty otherwise.
     */
    Optional<Volunteer> getVolunteerById(Long volunteerId);

    /**
     * Retrieves all volunteers.
     *
     * @return A list of all volunteers.
     */
    List<Volunteer> getAllVolunteers();

    /**
     * Associates a volunteer with an organization.
     *
     * @param volunteerId The ID of the volunteer.
     * @param organizationId The ID of the organization.
     * @return An Optional containing the created OrgVolunteer association if successful, or empty if volunteer/organization not found or association already exists.
     */
    Optional<OrgVolunteer> associateVolunteerWithOrganization(Long volunteerId, Long organizationId);

    /**
     * Dissociates a volunteer from an organization.
     *
     * @param volunteerId The ID of the volunteer.
     * @param organizationId The ID of the organization.
     * @return True if the association was successfully removed, false otherwise (e.g., association not found).
     */
    boolean dissociateVolunteerFromOrganization(Long volunteerId, Long organizationId);
}