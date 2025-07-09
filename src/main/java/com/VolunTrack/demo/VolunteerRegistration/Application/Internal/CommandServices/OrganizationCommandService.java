package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Services.IOrganizationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrganizationCommandService implements IOrganizationService {

    private final IOrganizationRepository organizationRepository;
    private final MessageSource messageSource;

    public OrganizationCommandService(IOrganizationRepository organizationRepository, MessageSource messageSource) {
        this.organizationRepository = organizationRepository;
        this.messageSource = messageSource;

    }

    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command) {
        if (organizationRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Organization with name " + command.name() + " already exists.");
        }
        if (organizationRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Organization with email " + command.email() + " already exists.");
        }

        Organization organization = new Organization(
                command.name(),
                command.description(),
                command.email(),
                command.plan()
        );
        return Optional.of(organizationRepository.save(organization));
    }

    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        return organizationRepository.findById(command.organizationId()).map(organization -> {
            if (command.name() != null && !command.name().equals(organization.getName()) && organizationRepository.existsByName(command.name())) {
                throw new IllegalArgumentException(
                        messageSource.getMessage("organization.exists.name", new Object[]{command.name()}, LocaleContextHolder.getLocale())
                );
            }
            if (command.email() != null && !command.email().equals(organization.getEmail()) && organizationRepository.existsByEmail(command.email())) {
                throw new IllegalArgumentException(
                        messageSource.getMessage("organization.exists.email", new Object[]{command.email()}, LocaleContextHolder.getLocale())
                );
            }

            organization.setName(command.name());
            organization.setDescription(command.description());
            organization.setEmail(command.email());
            organization.setPlan(command.plan());
            return organizationRepository.save(organization);
        });
    }

    @Override
    public void handle(DeleteOrganizationCommand command) {
        if (!organizationRepository.existsById(command.organizationId())) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("organization.not.found", new Object[]{command.organizationId()}, LocaleContextHolder.getLocale())
            );
        }
        organizationRepository.deleteById(command.organizationId());
    }


    @Override
    public Optional<Organization> handle(GetOrganizationQuery query) { // Única query para Organization
        throw new UnsupportedOperationException(
                messageSource.getMessage("organization.query.unsupported", null, LocaleContextHolder.getLocale())
        );
    }






}