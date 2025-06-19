package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IOrganizationRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Services.IOrganizationService; // Corrected path to IOrganizationService
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationQueryService implements IOrganizationService {

    private final IOrganizationRepository organizationRepository;

    public OrganizationQueryService(IOrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> handle(GetOrganizationQuery query) {
        return organizationRepository.findById(query.organizationId());
    }

    // Command handling methods (delegated to OrganizationCommandService)
    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by OrganizationCommandService");
    }

    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by OrganizationCommandService");
    }

    @Override
    public void handle(DeleteOrganizationCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by OrganizationCommandService");
    }


    public List<Organization> handle(List<GetOrganizationQuery> queries) {

        throw new UnsupportedOperationException("Querying multiple organizations with a list of GetOrganizationQuery is not supported by this service.");
    }
}