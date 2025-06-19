package com.VolunTrack.demo.VolunteerRegistration.Domain.Services;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices.OrganizationCommandService;
import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices.OrganizationQueryService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import org.springframework.stereotype.Service;

import java.util.List; // Keep this import if you have other List usages in the class
import java.util.Optional;

@Service
public class OrganizationService implements IOrganizationService {

    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;

    public OrganizationService(OrganizationCommandService organizationCommandService, OrganizationQueryService organizationQueryService) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
    }

    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command) {
        return organizationCommandService.handle(command);
    }

    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        return organizationCommandService.handle(command);
    }

    @Override
    public void handle(DeleteOrganizationCommand command) {
        organizationCommandService.handle(command);
    }

    @Override
    public Optional<Organization> handle(GetOrganizationQuery query) {
        return organizationQueryService.handle(query);
    }


}