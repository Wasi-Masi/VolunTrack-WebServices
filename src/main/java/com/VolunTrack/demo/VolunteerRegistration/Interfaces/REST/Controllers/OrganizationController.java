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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.VolunTrack.demo.response.ApiResponse;
import com.VolunTrack.demo.exception.ResourceNotFoundException; // Asegúrate de que esta excepción se maneje en GlobalExceptionHandler

/**
 * REST controller for Organization-related operations.
 * This controller handles incoming HTTP requests for organization management,
 * orchestrates interaction with application services, and returns appropriate HTTP responses with internationalized messages.
 */
@RestController
@RequestMapping(value = "/api/v1/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organizations", description = "Organization Management Endpoints")
public class OrganizationController {

    private final OrganizationCommandService organizationCommandService;
    private final OrganizationQueryService organizationQueryService;
    private final MessageSource messageSource;

    public OrganizationController(OrganizationCommandService organizationCommandService,
                                  OrganizationQueryService organizationQueryService,
                                  MessageSource messageSource) {
        this.organizationCommandService = organizationCommandService;
        this.organizationQueryService = organizationQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create an organization", description = "Creates a new organization in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationResource>> createOrganization(@RequestBody CreateOrganizationResource resource) {
        var command = CreateOrganizationCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            var organization = organizationCommandService.handle(command);
            return organization.map(value ->
                    new ResponseEntity<>(
                            ApiResponse.success(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value),
                                    messageSource.getMessage("organization.create.success", null, LocaleContextHolder.getLocale())),
                            HttpStatus.CREATED
                    )
            ).orElseGet(() ->
                    ResponseEntity.badRequest().body(ApiResponse.<OrganizationResource>error(
                            messageSource.getMessage("organization.create.error.failed", null, LocaleContextHolder.getLocale()), null))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<OrganizationResource>error(
                    e.getMessage(), null));
        }
    }

    @Operation(summary = "Get organization by ID", description = "Retrieves an organization's details by its unique identifier.")
    @GetMapping("/{organizationId}")
    public ResponseEntity<ApiResponse<OrganizationResource>> getOrganizationById(@PathVariable Long organizationId) {
        var getOrganizationQuery = new GetOrganizationQuery(organizationId);
        var organization = organizationQueryService.handle(getOrganizationQuery);
        return organization.map(value ->
                ResponseEntity.ok(ApiResponse.success(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value),
                        messageSource.getMessage("organization.get_by_id.success", null, LocaleContextHolder.getLocale())))
        ).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("organization.not.found.by.id", new Object[]{organizationId}, LocaleContextHolder.getLocale()))
        );
    }

    @Operation(summary = "Update an organization", description = "Updates the details of an existing organization.")
    @PutMapping("/{organizationId}")
    public ResponseEntity<ApiResponse<OrganizationResource>> updateOrganization(@PathVariable Long organizationId, @RequestBody UpdateOrganizationResource resource) {
        var command = UpdateOrganizationCommandFromResourceAssembler.toCommandFromResource(organizationId, resource);
        try {
            var updatedOrganization = organizationCommandService.handle(command);
            return updatedOrganization.map(value ->
                    ResponseEntity.ok(ApiResponse.success(OrganizationResourceFromEntityAssembler.toResourceFromEntity(value),
                            messageSource.getMessage("organization.update.success", null, LocaleContextHolder.getLocale())))
            ).orElseThrow(() ->
                    new ResourceNotFoundException(messageSource.getMessage("organization.not.found.by.id", new Object[]{organizationId}, LocaleContextHolder.getLocale()))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<OrganizationResource>error(
                    e.getMessage(), null));
        }
    }

    @Operation(summary = "Delete an organization", description = "Deletes an organization from the system by its unique identifier.")
    @DeleteMapping("/{organizationId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@PathVariable Long organizationId) {
        try {
            var command = new DeleteOrganizationCommand(organizationId);
            organizationCommandService.handle(command);
            return ResponseEntity.ok(ApiResponse.noContent(
                    messageSource.getMessage("organization.delete.success", null, LocaleContextHolder.getLocale())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(messageSource.getMessage("organization.delete.not.found", new Object[]{organizationId}, LocaleContextHolder.getLocale()), null)
            );
        }
    }
}