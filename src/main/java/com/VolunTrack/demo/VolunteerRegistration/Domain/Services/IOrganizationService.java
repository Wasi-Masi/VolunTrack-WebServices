package com.VolunTrack.demo.VolunteerRegistration.Domain.Services;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;

import java.util.Optional;

public interface IOrganizationService {
    Optional<Organization> handle(CreateOrganizationCommand command);
    Optional<Organization> handle(UpdateOrganizationCommand command);
    void handle(DeleteOrganizationCommand command);
    Optional<Organization> handle(GetOrganizationQuery query);
}