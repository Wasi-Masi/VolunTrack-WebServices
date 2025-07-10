package com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.CommandServices.VolunteerCommandService;
import com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices.VolunteerQueryService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.CreateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.DeleteVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Commands.UpdateVolunteerCommand;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetAllVolunteersQuery;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.CreateVolunteerResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.UpdateVolunteerResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Resources.VolunteerResource;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.CreateVolunteerCommandFromResourceAssembler;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.UpdateVolunteerCommandFromResourceAssembler;
import com.VolunTrack.demo.VolunteerRegistration.Interfaces.REST.Transform.VolunteerResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import com.VolunTrack.demo.response.ApiResponse;
import com.VolunTrack.demo.exception.ResourceNotFoundException;

/**
 * REST controller for Volunteer-related operations.
 * This controller handles incoming HTTP requests for volunteer management,
 * orchestrates interaction with application services, and returns appropriate HTTP responses.
 */
@RestController
@RequestMapping(value = "/api/v1/volunteers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Volunteers", description = "Volunteer Management Endpoints")
public class VolunteerController {

    private final VolunteerCommandService volunteerCommandService;
    private final VolunteerQueryService volunteerQueryService;
    private final CreateVolunteerCommandFromResourceAssembler createVolunteerCommandFromResourceAssembler;
    private final UpdateVolunteerCommandFromResourceAssembler updateVolunteerCommandFromResourceAssembler;
    private final VolunteerResourceFromEntityAssembler volunteerResourceFromEntityAssembler;
    private final MessageSource messageSource;

    public VolunteerController(VolunteerCommandService volunteerCommandService,
                               VolunteerQueryService volunteerQueryService,
                               CreateVolunteerCommandFromResourceAssembler createVolunteerCommandFromResourceAssembler,
                               UpdateVolunteerCommandFromResourceAssembler updateVolunteerCommandFromResourceAssembler,
                               VolunteerResourceFromEntityAssembler volunteerResourceFromEntityAssembler,
                               MessageSource messageSource) {
        this.volunteerCommandService = volunteerCommandService;
        this.volunteerQueryService = volunteerQueryService;
        this.createVolunteerCommandFromResourceAssembler = createVolunteerCommandFromResourceAssembler;
        this.updateVolunteerCommandFromResourceAssembler = updateVolunteerCommandFromResourceAssembler;
        this.volunteerResourceFromEntityAssembler = volunteerResourceFromEntityAssembler;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a new volunteer", description = "Creates a new volunteer with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Volunteer created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VolunteerResource.class))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Volunteer with given DNI or email already exists")
            })
    @PostMapping
    public ResponseEntity<ApiResponse<VolunteerResource>> createVolunteer(@RequestBody @Valid CreateVolunteerResource resource) {
        CreateVolunteerCommand command = createVolunteerCommandFromResourceAssembler.toCommandFromResource(resource);

        return volunteerCommandService.handle(command)
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .map(resourceResponse ->
                        new ResponseEntity<>(
                                ApiResponse.success(resourceResponse,
                                        messageSource.getMessage("volunteer.create.success", null, LocaleContextHolder.getLocale())),
                                HttpStatus.CREATED
                        ))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(ApiResponse.<VolunteerResource>error(
                                        messageSource.getMessage("volunteer.create.error.conflict", null, LocaleContextHolder.getLocale()), null))
                );
    }

    @Operation(summary = "Get all volunteers", description = "Retrieves a list of all registered volunteers.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<VolunteerResource>>> getAllVolunteers() {
        GetAllVolunteersQuery query = new GetAllVolunteersQuery();

        List<Volunteer> volunteers = volunteerQueryService.handle(query);

        List<VolunteerResource> volunteerResources = volunteers.stream()
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(volunteerResources,
                messageSource.getMessage("volunteer.get_all.success", null, LocaleContextHolder.getLocale())));
    }

    @Operation(summary = "Update an existing volunteer", description = "Updates an existing volunteer's details.")
    @PutMapping("/{volunteerId}")
    public ResponseEntity<ApiResponse<VolunteerResource>> updateVolunteer(@PathVariable Long volunteerId,
                                                                          @RequestBody @Valid UpdateVolunteerResource resource) {

        UpdateVolunteerCommand command = updateVolunteerCommandFromResourceAssembler.toCommandFromResource(volunteerId, resource);

        return volunteerCommandService.handle(command)
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .map(resourceResponse ->
                        ResponseEntity.ok(ApiResponse.success(resourceResponse,
                                messageSource.getMessage("volunteer.update.success", null, LocaleContextHolder.getLocale())))
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                messageSource.getMessage("volunteer.not.found.by.id", new Object[]{volunteerId}, LocaleContextHolder.getLocale()))
                );
    }

    @Operation(summary = "Delete a volunteer by ID", description = "Deletes a volunteer using their unique identifier.")
    @DeleteMapping("/{volunteerId}")
    public ResponseEntity<ApiResponse<Void>> deleteVolunteer(@PathVariable Long volunteerId) {
        DeleteVolunteerCommand command = new DeleteVolunteerCommand(volunteerId);

        boolean deleted = volunteerCommandService.handle(command);

        if (deleted) {
            return ResponseEntity.ok(ApiResponse.noContent(
                    messageSource.getMessage("volunteer.delete.success", null, LocaleContextHolder.getLocale())));
        } else {
            throw new ResourceNotFoundException(
                    messageSource.getMessage("volunteer.not.found.by.id", new Object[]{volunteerId}, LocaleContextHolder.getLocale()));
        }
    }
}