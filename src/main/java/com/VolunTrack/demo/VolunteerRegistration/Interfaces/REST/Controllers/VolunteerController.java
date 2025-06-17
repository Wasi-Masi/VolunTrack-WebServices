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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    public VolunteerController(VolunteerCommandService volunteerCommandService,
                               VolunteerQueryService volunteerQueryService,
                               CreateVolunteerCommandFromResourceAssembler createVolunteerCommandFromResourceAssembler,
                               UpdateVolunteerCommandFromResourceAssembler updateVolunteerCommandFromResourceAssembler,
                               VolunteerResourceFromEntityAssembler volunteerResourceFromEntityAssembler) {
        this.volunteerCommandService = volunteerCommandService;
        this.volunteerQueryService = volunteerQueryService;
        this.createVolunteerCommandFromResourceAssembler = createVolunteerCommandFromResourceAssembler;
        this.updateVolunteerCommandFromResourceAssembler = updateVolunteerCommandFromResourceAssembler;
        this.volunteerResourceFromEntityAssembler = volunteerResourceFromEntityAssembler;
    }

    /**
     * Creates a new volunteer.
     * POST /api/v1/volunteers
     *
     * @param resource The resource containing the data for the new volunteer.
     * @return A ResponseEntity containing the created VolunteerResource and 201 Created status,
     * or a 400 Bad Request if validation fails, or 409 Conflict if DNI/Email already exists.
     */
    @Operation(summary = "Create a new volunteer", description = "Creates a new volunteer with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Volunteer created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VolunteerResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "409", description = "Volunteer with given DNI or email already exists")
            })
    @PostMapping
    public ResponseEntity<VolunteerResource> createVolunteer(@RequestBody @Valid CreateVolunteerResource resource) {
        CreateVolunteerCommand command = createVolunteerCommandFromResourceAssembler.toCommandFromResource(resource);

        return volunteerCommandService.handle(command)
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .map(resourceResponse -> new ResponseEntity<>(resourceResponse, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    /**
     * Retrieves all volunteers.
     * GET /api/v1/volunteers
     *
     * @return A ResponseEntity containing a list of VolunteerResources and 200 OK status.
     */
    @Operation(summary = "Get all volunteers", description = "Retrieves a list of all registered volunteers.")
    @GetMapping
    public ResponseEntity<List<VolunteerResource>> getAllVolunteers() {
        GetAllVolunteersQuery query = new GetAllVolunteersQuery();

        List<Volunteer> volunteers = volunteerQueryService.handle(query);

        List<VolunteerResource> volunteerResources = volunteers.stream()
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return new ResponseEntity<>(volunteerResources, HttpStatus.OK);
    }



    /**
     * Updates an existing volunteer.
     * PUT /api/v1/volunteers/{volunteerId}
     *
     * @param volunteerId The ID of the volunteer to update.
     * @param resource The resource containing the updated data.
     * @return A ResponseEntity containing the updated VolunteerResource and 200 OK status,
     * or a 404 Not Found if the volunteer does not exist, or 400 Bad Request/409 Conflict for invalid data.
     */
    @Operation(summary = "Update an existing volunteer", description = "Updates an existing volunteer's details.")
    @PutMapping("/{volunteerId}")
    public ResponseEntity<VolunteerResource> updateVolunteer(@PathVariable Long volunteerId,
                                                             @RequestBody @Valid UpdateVolunteerResource resource) {

        UpdateVolunteerCommand command = updateVolunteerCommandFromResourceAssembler.toCommandFromResource(volunteerId, resource);


        return volunteerCommandService.handle(command)
                .map(volunteerResourceFromEntityAssembler::toResourceFromEntity)
                .map(resourceResponse -> new ResponseEntity<>(resourceResponse, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a volunteer by their ID.
     * DELETE /api/v1/volunteers/{volunteerId}
     *
     * @param volunteerId The ID of the volunteer to delete.
     * @return A ResponseEntity with 204 No Content status if successful, or 404 Not Found if the volunteer does not exist.
     */
    @Operation(summary = "Delete a volunteer by ID", description = "Deletes a volunteer using their unique identifier.")
    @DeleteMapping("/{volunteerId}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long volunteerId) {
        DeleteVolunteerCommand command = new DeleteVolunteerCommand(volunteerId);

        boolean deleted = volunteerCommandService.handle(command);

        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}