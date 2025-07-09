package com.VolunTrack.demo.VolunteerRegistration.Domain.Services;

import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.OrgVolunteerId;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.VolunteerStatus;
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
     *
     * Modificado para incluir 'organizationId' y 'profession'
     * y asociar al voluntario con la organización al crearlo.
     */
    @Override
    @Transactional
    public Optional<Volunteer> createVolunteer(String firstName, String lastName, String dni, LocalDate dateOfBirth,
                                               String email, String phoneNumber, String address,
                                               Long organizationId, String profession) {

        if (volunteerRepository.existsByDni(dni)) {
            System.out.println("Error: Volunteer with DNI " + dni + " already exists.");
            return Optional.empty();
        }
        if (volunteerRepository.existsByEmail(email)) {
            System.out.println("Error: Volunteer with email " + email + " already exists.");
            return Optional.empty();
        }

        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isEmpty()) {
            System.out.println("Error: Organization with ID " + organizationId + " not found.");
            return Optional.empty();
        }

        // --- ¡AQUÍ ESTÁ EL CAMBIO CLAVE! ---
        // Ahora usamos el constructor que incluye el organizationId
        Volunteer volunteer = new Volunteer(firstName, lastName, dni, dateOfBirth, email, phoneNumber, address, profession, organizationId);
        // --- FIN DEL CAMBIO CLAVE ---


        Volunteer savedVolunteer = volunteerRepository.save(volunteer);

        Optional<OrgVolunteer> association = associateVolunteerWithOrganization(savedVolunteer.getId(), organizationId);

        if (association.isEmpty()) {
            System.out.println("Error: Failed to associate volunteer with organization after creation, or association already exists.");
            // Considerar rollback si la asociación es obligatoria para el modelo
            return Optional.empty();
        }

        unitOfWork.complete();

        return Optional.of(savedVolunteer);
    }

    /**
     * {@inheritDoc}
     *
     * Modificado para permitir la actualización de la profesión y el estado.
     */
    @Override
    @Transactional
    public Optional<Volunteer> updateVolunteer(Long volunteerId, String firstName, String lastName, String dni,
                                               LocalDate dateOfBirth, String email, String phoneNumber,
                                               String address, String profession, VolunteerStatus status) {

        Optional<Volunteer> existingVolunteerOptional = volunteerRepository.findById(volunteerId);
        if (existingVolunteerOptional.isEmpty()) {
            return Optional.empty();
        }

        Volunteer existingVolunteer = existingVolunteerOptional.get();

        if (dni != null && !dni.equals(existingVolunteer.getDni()) && volunteerRepository.existsByDni(dni)) {
            System.out.println("Error: Cannot update DNI to " + dni + " as it already exists for another volunteer.");
            return Optional.empty();
        }
        if (email != null && !email.equals(existingVolunteer.getEmail()) && volunteerRepository.existsByEmail(email)) {
            System.out.println("Error: Cannot update email to " + email + " as it already exists for another volunteer.");
            return Optional.empty();
        }

        if (firstName != null) existingVolunteer.setFirstName(firstName);
        if (lastName != null) existingVolunteer.setLastName(lastName);
        if (dni != null) existingVolunteer.setDni(dni);
        if (dateOfBirth != null) existingVolunteer.setDateOfBirth(dateOfBirth);
        if (email != null) existingVolunteer.setEmail(email);
        if (phoneNumber != null) existingVolunteer.setPhoneNumber(phoneNumber);
        if (address != null) existingVolunteer.setAddress(address);
        if (profession != null) existingVolunteer.setProfession(profession);
        if (status != null) existingVolunteer.setStatus(status);

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
     * Este método ya maneja su propia transacción y llama a unitOfWork.complete().
     * Cuando se llama desde `createVolunteer` (que también es @Transactional),
     * se unen en una única transacción.
     */
    @Override
    @Transactional
    public Optional<OrgVolunteer> associateVolunteerWithOrganization(Long volunteerId, Long organizationId) {
        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(volunteerId);
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if (volunteerOptional.isEmpty() || organizationOptional.isEmpty()) {
            System.out.println("Error: Volunteer or Organization not found for association.");
            return Optional.empty();
        }

        Volunteer volunteer = volunteerOptional.get();
        Organization organization = organizationOptional.get();

        OrgVolunteerId orgVolunteerId = new OrgVolunteerId(organizationId, volunteerId);
        boolean associationExists = organization.getOrgVolunteers().stream()
                .anyMatch(ov -> ov.getId().equals(orgVolunteerId));
        if (associationExists) {
            System.out.println("Error: Association between Volunteer " + volunteerId + " and Organization " + organizationId + " already exists.");
            return Optional.empty();
        }

        OrgVolunteer orgVolunteer = new OrgVolunteer(organization, volunteer);

        organization.addOrgVolunteer(orgVolunteer);
        volunteer.addOrgVolunteer(orgVolunteer);

        organizationRepository.save(organization);
        volunteerRepository.save(volunteer);

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
            System.out.println("Error: Volunteer or Organization not found for dissociation.");
            return false;
        }

        Volunteer volunteer = volunteerOptional.get();
        Organization organization = organizationOptional.get();

        OrgVolunteerId orgVolunteerId = new OrgVolunteerId(organizationId, volunteerId);

        Optional<OrgVolunteer> associationToRemove = organization.getOrgVolunteers().stream()
                .filter(ov -> ov.getId().equals(orgVolunteerId))
                .findFirst();

        if (associationToRemove.isEmpty()) {
            System.out.println("Error: Association between Volunteer " + volunteerId + " and Organization " + organizationId + " does not exist.");
            return false;
        }

        organization.removeOrgVolunteer(associationToRemove.get());
        volunteer.removeOrgVolunteer(associationToRemove.get());

        organizationRepository.save(organization);
        volunteerRepository.save(volunteer);
        unitOfWork.complete();

        return true;
    }
}