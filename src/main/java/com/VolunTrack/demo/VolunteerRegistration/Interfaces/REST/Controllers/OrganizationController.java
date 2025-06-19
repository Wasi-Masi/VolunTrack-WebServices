package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices.OrganizationCommandService;
import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices.OrganizationQueryService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteOrganizationCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetOrganizationQuery;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.CreateOrganizationResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.OrganizationResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.UpdateOrganizationResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.CreateOrganizationCommandFromResourceAssembler;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.OrganizationResourceFromEntityAssembler;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.UpdateOrganizationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;




@RestController
@RequestMapping(value = "/api/v1/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organizations", description = "Organization Management Endpoints")
public class OrganizationController {

    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;

    public OrganizationController(OrganizationCommandService organizationCommandService, OrganizationQueryService organizationQueryService) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
    }

    /**
     * Creates a new organization.
     * POST /api/v1/organizations
     *
     * @param resource The resource containing the organization's data.
     * @return A ResponseEntity with the created OrganizationResource (HTTP 201 Created) or bad request (HTTP 400).
     */
    @Operation(summary = "Create an organization", description = "Creates a new organization in the system.")
    @PostMapping
    public ResponseEntity<OrganizationResource> createOrganization(@RequestBody CreateOrganizationResource resource) {
        var command = CreateOrganizationCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            var organization = organizationCommandService.handle(command);
            return organization.map(value ->
                    new ResponseEntity<>(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
            ).orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build();
        }
    }

    /**
     * Retrieves an organization by its ID.
     * GET /api/v1/organizations/{organizationId}
     *
     * @param organizationId The ID of the organization to retrieve.
     * @return A ResponseEntity with the OrganizationResource (HTTP 200 OK) or not found (HTTP 404).
     */
    @Operation(summary = "Get organization by ID", description = "Retrieves an organization's details by its unique identifier.")
    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResource> getOrganizationById(@PathVariable Long organizationId) {
        var getOrganizationQuery = new GetOrganizationQuery(organizationId); // Usa tu query
        var organization = organizationQueryService.handle(getOrganizationQuery);
        return organization.map(value ->
                ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing organization.
     * PUT /api/v1/organizations/{organizationId}
     *
     * @param organizationId The ID of the organization to update.
     * @param resource The resource containing the updated organization data.
     * @return A ResponseEntity with the updated OrganizationResource (HTTP 200 OK) or not found/bad request.
     */
    @Operation(summary = "Update an organization", description = "Updates the details of an existing organization.")
    @PutMapping("/{organizationId}")
    public ResponseEntity<OrganizationResource> updateOrganization(@PathVariable Long organizationId, @RequestBody UpdateOrganizationResource resource) {
        var command = UpdateOrganizationCommandFromResourceAssembler.toCommandFromResource(organizationId, resource);
        try {
            var updatedOrganization = organizationCommandService.handle(command);
            return updatedOrganization.map(value ->
                    ResponseEntity.ok(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value))
            ).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build();
        }
    }

    /**
     * Deletes an organization by its ID.
     * DELETE /api/v1/organizations/{organizationId}
     *
     * @param organizationId The ID of the organization to delete.
     * @return A ResponseEntity with no content (HTTP 204 No Content) or not found (HTTP 404).
     */
    @Operation(summary = "Delete an organization", description = "Deletes an organization from the system by its unique identifier.")
    @DeleteMapping("/{organizationId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable Long organizationId) {
        try {
            var command = new DeleteOrganizationCommand(organizationId);
            organizationCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}