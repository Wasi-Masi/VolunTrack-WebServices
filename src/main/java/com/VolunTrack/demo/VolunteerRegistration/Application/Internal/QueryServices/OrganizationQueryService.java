package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Services.IOrganizationService; // Corrected path to IOrganizationService
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.context.MessageSource;
import java.util.Locale;

@Service
public class OrganizationQueryService implements IOrganizationService {

    private final IOrganizationRepository organizationRepository;
    private final MessageSource messageSource;

    public OrganizationQueryService(IOrganizationRepository organizationRepository, MessageSource messageSource) {
        this.organizationRepository = organizationRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Optional<Organization> handle(GetOrganizationQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    // Command handling methods (delegated to OrganizationCommandService)
    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command) {
        throw new UnsupportedOperationException(
                messageSource.getMessage("organization.command.unsupported.create", null, LocaleContextHolder.getLocale())
        );
    }

    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        throw new UnsupportedOperationException(
                messageSource.getMessage("organization.command.unsupported.update", null, LocaleContextHolder.getLocale())
        );
    }

    @Override
    public void handle(DeleteOrganizationCommand command) {
        throw new UnsupportedOperationException(
                messageSource.getMessage("organization.command.unsupported.delete", null, LocaleContextHolder.getLocale())
        );
    }

    public List<Organization> handle(List<GetOrganizationQuery> queries) {
        throw new UnsupportedOperationException(
                messageSource.getMessage("organization.query.unsupported.multiple", null, LocaleContextHolder.getLocale())
        );
    }

}