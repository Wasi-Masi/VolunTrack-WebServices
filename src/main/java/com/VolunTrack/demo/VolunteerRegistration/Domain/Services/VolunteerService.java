package com.VolunTrack.demo.VolunteerRegistration.Domain.Services;

import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteerId;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Domain Service implementation for Volunteer-related business operations.
 * This class orchestrates interactions between repositories and applies business rules.
 * It's annotated with {@code @Service} to be managed by Spring as a service component.
 */
@Service
public class VolunteerService implements IVolunteerService {

    private final IVolunteerRepository volunteerRepository;
    private final IOrganizationRepository organizationRepository;
    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a new VolunteerService with the given repositories and unit of work.
     * Dependencies are injected by Spring.
     *
     * @param volunteerRepository The repository for Volunteer entities.
     * @param organizationRepository The repository for Organization entities.
     * @param unitOfWork The unit of work for managing transactions.
     */
    public VolunteerService(IVolunteerRepository volunteerRepository, IOrganizationRepository organizationRepository, IUnitOfWork unitOfWork) {
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
        this.unitOfWork = unitOfWork;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Volunteer> createVolunteer(String firstName, String lastName, String dni, LocalDate dateOfBirth, String email, String phoneNumber, String address) {
        if (volunteerRepository.existsByDni(dni)) {
            return Optional.empty();
        }
        if (volunteerRepository.existsByEmail(email)) {
            return Optional.empty();
        }

        Volunteer volunteer = new Volunteer(firstName, lastName, dni, dateOfBirth, email, phoneNumber, address);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        unitOfWork.complete();

        return Optional.of(savedVolunteer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Volunteer> updateVolunteer(Long volunteerId, String firstName, String lastName, String dni, LocalDate dateOfBirth, String email, String phoneNumber, String address) {
        Optional<Volunteer> existingVolunteerOptional = volunteerRepository.findById(volunteerId);
        if (existingVolunteerOptional.isEmpty()) {
            return Optional.empty();
        }

        Volunteer existingVolunteer = existingVolunteerOptional.get();

        if (dni != null && !dni.equals(existingVolunteer.getDni()) && volunteerRepository.existsByDni(dni)) {
            return Optional.empty();
        }
        if (email != null && !email.equals(existingVolunteer.getEmail()) && volunteerRepository.existsByEmail(email)) {
            return Optional.empty();
        }

        if (firstName != null) existingVolunteer.setFirstName(firstName);
        if (lastName != null) existingVolunteer.setLastName(lastName);
        if (dni != null) existingVolunteer.setDni(dni);
        if (dateOfBirth != null) existingVolunteer.setDateOfBirth(dateOfBirth);
        if (email != null) existingVolunteer.setEmail(email);
        if (phoneNumber != null) existingVolunteer.setPhoneNumber(phoneNumber);
        if (address != null) existingVolunteer.setAddress(address);

        Volunteer updatedVolunteer = volunteerRepository.save(existingVolunteer);
        unitOfWork.complete();

        return Optional.of(updatedVolunteer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean deleteVolunteer(Long volunteerId) {
        if (!volunteerRepository.existsById(volunteerId)) {
            return false;
        }
        volunteerRepository.deleteById(volunteerId);
        unitOfWork.complete();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Volunteer> getVolunteerById(Long volunteerId) {
        return volunteerRepository.findById(volunteerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<OrgVolunteer> associateVolunteerWithOrganization(Long volunteerId, Long organizationId) {
        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(volunteerId);
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if (volunteerOptional.isEmpty() || organizationOptional.isEmpty()) {
            return Optional.empty();
        }

        Volunteer volunteer = volunteerOptional.get();
        Organization organization = organizationOptional.get();


        OrgVolunteerId orgVolunteerId = new OrgVolunteerId(organizationId, volunteerId);
        boolean associationExists = organization.getOrgVolunteers().stream()
                .anyMatch(ov -> ov.getId().equals(orgVolunteerId));
        if (associationExists) {
            return Optional.empty();
        }

        OrgVolunteer orgVolunteer = new OrgVolunteer(organization, volunteer);

        organization.addOrgVolunteer(orgVolunteer);
        volunteer.addOrgVolunteer(orgVolunteer);

        organizationRepository.save(organization);
        unitOfWork.complete();

        return Optional.of(orgVolunteer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean dissociateVolunteerFromOrganization(Long volunteerId, Long organizationId) {
        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(volunteerId);
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if (volunteerOptional.isEmpty() || organizationOptional.isEmpty()) {
            return false;
        }

        Volunteer volunteer = volunteerOptional.get();
        Organization organization = organizationOptional.get();

        OrgVolunteerId orgVolunteerId = new OrgVolunteerId(organizationId, volunteerId);

        Optional<OrgVolunteer> associationToRemove = organization.getOrgVolunteers().stream()
                .filter(ov -> ov.getId().equals(orgVolunteerId))
                .findFirst();

        if (associationToRemove.isEmpty()) {
            return false;
        }

        organization.removeOrgVolunteer(associationToRemove.get());
        volunteer.removeOrgVolunteer(associationToRemove.get());


        organizationRepository.save(organization);
        unitOfWork.complete();

        return true;
    }
}