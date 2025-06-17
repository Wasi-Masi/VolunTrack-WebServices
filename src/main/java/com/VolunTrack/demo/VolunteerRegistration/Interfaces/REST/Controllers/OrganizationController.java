package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices.OrganizationQueryService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.OrganizationResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.OrganizationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for Organization-related operations.
 * This controller handles incoming HTTP requests, orchestrates interaction with application services,
 * and returns appropriate HTTP responses.
 */
@RestController
@RequestMapping(value = "/api/v1/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organizations", description = "Organization Management Endpoints")
public class OrganizationController {

    private final OrganizationQueryService organizationQueryService;
    private final OrganizationResourceFromEntityAssembler organizationResourceFromEntityAssembler;

    public OrganizationController(OrganizationQueryService organizationQueryService, OrganizationResourceFromEntityAssembler organizationResourceFromEntityAssembler) {
        this.organizationQueryService = organizationQueryService;
        this.organizationResourceFromEntityAssembler = organizationResourceFromEntityAssembler;
    }


    /**
     * Retrieves the single organization available in the system.
     * GET /api/v1/organizations
     *
     * @return A ResponseEntity containing the OrganizationResource if found (HTTP 200 OK),
     * or a 404 Not Found status if no organization is present.
     */
    @Operation(summary = "Get organization", description = "Retrieves the details of the single organization available in the system.")
    @GetMapping
    public ResponseEntity<OrganizationResource> getTheSingleOrganization() {
        Optional<Organization> organization = organizationQueryService.getTheSingleOrganization();

        return organization.map(o -> new ResponseEntity<>(organizationResourceFromEntityAssembler.toResourceFromEntity(o), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}