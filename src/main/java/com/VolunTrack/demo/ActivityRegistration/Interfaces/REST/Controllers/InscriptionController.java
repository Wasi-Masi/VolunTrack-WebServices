package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.InscriptionCommandService;
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.InscriptionQueryService;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateInscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.InscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.RegisteredVolunteerResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateInscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateInscriptionCommandFromResourceAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.InscriptionResourceFromEntityAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateInscriptionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

// Nuevas importaciones para ApiResponse y MessageSource
import com.VolunTrack.demo.response.ApiResponse;
import com.VolunTrack.demo.exception.ResourceNotFoundException; // Asumiendo que esta excepción es manejada por tu GlobalExceptionHandler
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * REST controller for managing Inscription entities.
 * This class provides endpoints for creating, retrieving, updating, and deleting inscriptions,
 * interacting with InscriptionCommandService and InscriptionQueryService.
 */
@RestController
@RequestMapping(value = "/api/v1/inscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inscriptions", description = "Operations related to enrollments in activities")
public class InscriptionController {

    private final InscriptionCommandService inscriptionCommandService;
    private final InscriptionQueryService inscriptionQueryService;
    private final IVolunteerRepository volunteerRepository;
    private final MessageSource messageSource;

    /**
     * Constructor to inject the required command and query services.
     *
     * @param inscriptionCommandService - The service for handling commands related to inscriptions.
     * @param inscriptionQueryService - The service for handling queries related to inscriptions.
     * @param volunteerRepository - The repository for accessing volunteer data.
     * @param messageSource - The service for resolving messages, with support for internationalization.
     */
    public InscriptionController(InscriptionCommandService inscriptionCommandService,
                                 InscriptionQueryService inscriptionQueryService,
                                 IVolunteerRepository volunteerRepository,
                                 MessageSource messageSource) {
        this.inscriptionCommandService = inscriptionCommandService;
        this.inscriptionQueryService = inscriptionQueryService;
        this.volunteerRepository = volunteerRepository;
        this.messageSource = messageSource;
    }

    /**
     * Handles the request to create a new inscription.
     *
     * @param resource - The resource containing data for creating the inscription.
     * @return A ResponseEntity with the created inscription's resource or a bad request response if creation fails.
     */
    @Operation(summary = "Create a inscription", description = "Creates a new inscription in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<InscriptionResource>> createInscription(@RequestBody CreateInscriptionResource resource) {
        var command = CreateInscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            var inscription = inscriptionCommandService.handle(command);
            return inscription.map(value ->
                    new ResponseEntity<>(
                            ApiResponse.success(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value),
                                    messageSource.getMessage("inscription.create.success", null, LocaleContextHolder.getLocale())),
                            HttpStatus.CREATED
                    )
            ).orElseGet(() ->
                    ResponseEntity.badRequest().body(ApiResponse.<InscriptionResource>error(
                            messageSource.getMessage("inscription.create.error.failed_no_result", null, LocaleContextHolder.getLocale()), null))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<InscriptionResource>error(
                    e.getMessage(), null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.<InscriptionResource>error(
                    e.getMessage(), null));
        }
    }

    /**
     * Handles the request to get all inscriptions.
     *
     * @return A ResponseEntity containing a list of all inscriptions as resources.
     */
    @Operation(summary = "Get all inscriptions", description = "Retrieves a list of all registered inscriptions.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InscriptionResource>>> getAllInscriptions() {
        var getAllInscriptionsQuery = new GetAllInscriptionsQuery();
        var inscriptions = inscriptionQueryService.handle(getAllInscriptionsQuery);
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(inscriptionResources,
                messageSource.getMessage("inscription.get_all.success", null, LocaleContextHolder.getLocale())));
    }

    /**
     * Handles the request to get an inscription by its ID.
     *
     * @param inscriptionId - The ID of the inscription to fetch.
     * @return A ResponseEntity containing the inscription resource if found, or a not found response if not found.
     */
    @Operation(summary = "Get inscription by ID", description = "Retrieves an inscription's details by its unique identifier.")
    @GetMapping("/{inscriptionId}")
    public ResponseEntity<ApiResponse<InscriptionResource>> getInscriptionById(@PathVariable Long inscriptionId) {
        var getInscriptionByIdQuery = new GetInscriptionByIdQuery(inscriptionId);
        var inscription = inscriptionQueryService.handle(getInscriptionByIdQuery);
        return inscription.map(value ->
                ResponseEntity.ok(ApiResponse.success(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value),
                        messageSource.getMessage("inscription.get_by_id.success", null, LocaleContextHolder.getLocale())))
        ).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("inscription.not.found.by.id", new Object[]{inscriptionId}, LocaleContextHolder.getLocale()))
        );
    }

    /**
     * Handles the request to get inscriptions by a specific activity ID.
     *
     * @param activityId - The ID of the activity to get inscriptions for.
     * @return A ResponseEntity containing the list of inscriptions related to the activity.
     */
    @Operation(summary = "Get inscription by activity ID", description = "Retrieves an inscription's details by activity ID.")
    @GetMapping("/byActivity/{activityId}")
    public ResponseEntity<ApiResponse<List<InscriptionResource>>> getInscriptionsByActivityId(@PathVariable Long activityId) {
        var query = new GetInscriptionsByActivityIdQuery(activityId);
        var inscriptions = inscriptionQueryService.handle(query);
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        if (inscriptionResources.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(inscriptionResources,
                    messageSource.getMessage("inscription.get_by_activity_id.no_data", new Object[]{activityId}, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok(ApiResponse.success(inscriptionResources,
                messageSource.getMessage("inscription.get_by_activity_id.success", null, LocaleContextHolder.getLocale())));
    }

    /**
     * Handles the request to update an existing inscription.
     *
     * @param inscriptionId - The ID of the inscription to update.
     * @param resource - The resource containing the updated inscription data.
     * @return A ResponseEntity containing the updated inscription resource, or a not found response if the inscription was not found.
     */
    @Operation(summary = "Update an inscription", description = "Updates the details of an existing inscription.")
    @PutMapping("/{inscriptionId}")
    public ResponseEntity<ApiResponse<InscriptionResource>> updateInscription(@PathVariable Long inscriptionId, @RequestBody UpdateInscriptionResource resource) {
        var command = UpdateInscriptionCommandFromResourceAssembler.toCommandFromResource(inscriptionId, resource);
        try {
            var updatedInscription = inscriptionCommandService.handle(command);
            return updatedInscription.map(value ->
                    ResponseEntity.ok(ApiResponse.success(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value),
                            messageSource.getMessage("inscription.update.success", null, LocaleContextHolder.getLocale())))
            ).orElseThrow(() ->
                    new ResourceNotFoundException(messageSource.getMessage("inscription.not.found.by.id", new Object[]{inscriptionId}, LocaleContextHolder.getLocale()))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<InscriptionResource>error(
                    e.getMessage(), null));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.<InscriptionResource>error(
                    e.getMessage(), null));
        }
    }

    /**
     * Handles the request to delete an inscription by its ID.
     *
     * @param inscriptionId - The ID of the inscription to delete.
     * @return A ResponseEntity indicating the result of the deletion.
     */
    @Operation(summary = "Delete an inscription", description = "Deletes an inscription from the system by its unique identifier.")
    @DeleteMapping("/{inscriptionId}")
    public ResponseEntity<ApiResponse<Void>> deleteInscription(@PathVariable Long inscriptionId) {
        try {
            var command = new DeleteInscriptionCommand(inscriptionId);
            inscriptionCommandService.handle(command);
            return ResponseEntity.ok(ApiResponse.noContent(
                    messageSource.getMessage("inscription.delete.success", null, LocaleContextHolder.getLocale())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(messageSource.getMessage("inscription.delete.not.found", new Object[]{inscriptionId}, LocaleContextHolder.getLocale()), null)
            );
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.error(messageSource.getMessage("inscription.delete.error.internal_state", null, LocaleContextHolder.getLocale()), null)
            );
        }
    }

    @Operation(summary = "Get registered volunteers for an activity", description = "Returns all volunteer info for those registered in a specific activity")
    @GetMapping("/volunteers/byActivity/{activityId}")
    public ResponseEntity<ApiResponse<List<RegisteredVolunteerResource>>> getRegisteredVolunteersForActivity(@PathVariable Long activityId) {
        var inscriptions = inscriptionQueryService.handle(new GetInscriptionsByActivityIdQuery(activityId));

        var volunteers = inscriptions.stream()
                .map(inscription -> {
                    var volunteer = volunteerRepository.findById(inscription.getVoluntarioId())
                            .orElse(null);
                    if (volunteer == null) return null;
                    return new RegisteredVolunteerResource(
                            volunteer.getId(),
                            volunteer.getFirstName(),
                            volunteer.getLastName(),
                            volunteer.getEmail(),
                            volunteer.getPhoneNumber(),
                            volunteer.getDateOfBirth(),
                            volunteer.getProfession(),
                            volunteer.getRegistrationDate(),
                            volunteer.getStatus().toString(),
                            inscription.getInscription_id(),
                            inscription.getFecha(),
                            inscription.getEstado().toString()
                    );
                })
                .filter(v -> v != null)
                .collect(Collectors.toList());

        if (volunteers.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(volunteers,
                    messageSource.getMessage("registered_volunteers.get.no_data", new Object[]{activityId}, LocaleContextHolder.getLocale())));
        }

        return ResponseEntity.ok(ApiResponse.success(volunteers,
                messageSource.getMessage("registered_volunteers.get.success", null, LocaleContextHolder.getLocale())));
    }
}