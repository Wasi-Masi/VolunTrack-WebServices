package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices.OrganizationQueryService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Organization;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.OrganizationResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.OrganizationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Retrieves an organization by its ID.
     * GET /api/v1/organizations/{organizationId}
     *
     * @param organizationId The ID of the organization to retrieve.
     * @return A ResponseEntity containing the OrganizationResource if found, or a 404 Not Found status.
     */
    @Operation(summary = "Get an organization by ID", description = "Retrieves an organization's details by its unique identifier.")
    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResource> getOrganizationById(@PathVariable Long organizationId) {
        GetOrganizationQuery query = new GetOrganizationQuery(organizationId);

        return organizationQueryService.handle(query)
                .map(organizationResourceFromEntityAssembler::toResourceFromEntity)
                .map(resource -> new ResponseEntity<>(resource, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}