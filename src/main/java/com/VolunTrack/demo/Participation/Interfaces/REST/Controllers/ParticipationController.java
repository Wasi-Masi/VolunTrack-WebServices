package com.VolunTrack.demo.Participation.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Participation.Application.Internal.CommandServices.ParticipationCommandService;
import com.VolunTrack.demo.Participation.Application.Internal.QueryServices.ParticipationQueryService;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Commands.DeleteParticipationCommand;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByActivityIdQuery;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByUserIdQuery;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateParticipationResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.ParticipationResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CreateParticipationCommandFromResourceAssembler;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.ParticipationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Nuevas importaciones para ApiResponse y MessageSource
import com.VolunTrack.demo.response.ApiResponse;
import com.VolunTrack.demo.exception.ResourceNotFoundException; // Asumiendo que esta excepción es manejada por tu GlobalExceptionHandler
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * REST Controller for managing Participation records.
 */
@RestController
@RequestMapping(value = "/api/v1/participations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Participations", description = "Participation Management Endpoints")
public class ParticipationController {

    private final ParticipationCommandService participationCommandService;
    private final ParticipationQueryService participationQueryService;
    private final MessageSource messageSource;

    public ParticipationController(ParticipationCommandService participationCommandService,
                                   ParticipationQueryService participationQueryService,
                                   MessageSource messageSource) {
        this.participationCommandService = participationCommandService;
        this.participationQueryService = participationQueryService;
        this.messageSource = messageSource;
    }

    /**
     * Endpoint to create a new participation record.
     * POST /api/v1/participations
     */
    @Operation(summary = "Create a participation", description = "Creates a new participation in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<ParticipationResource>> createParticipation(@RequestBody @Valid CreateParticipationResource resource) {
        Optional<Participation> participation = participationCommandService.handle(
                CreateParticipationCommandFromResourceAssembler.toCommandFromResource(resource));

        return participation.map(p -> new ResponseEntity<>(
                ApiResponse.success(ParticipationResourceFromEntityAssembler.toResourceFromEntity(p),
                        messageSource.getMessage("participation.create.success", null, LocaleContextHolder.getLocale())),
                HttpStatus.CREATED)
        ).orElseGet(() -> ResponseEntity.badRequest().body(
                ApiResponse.<ParticipationResource>error(
                        messageSource.getMessage("participation.create.error.failed_no_result", null, LocaleContextHolder.getLocale()), null))
        );
    }

    /**
     * Endpoint to get participation records by volunteer ID.
     * GET /api/v1/participations/by-volunteer/{volunteerId}
     */
    @Operation(summary = "Get participation by volunteer ID", description = "Retrieves a participation's details by volunteer ID.")
    @GetMapping("by-volunteer/{volunteerId}")
    public ResponseEntity<ApiResponse<List<ParticipationResource>>> getParticipationsByVolunteerId(@PathVariable Long volunteerId) {
        List<Participation> participations = participationQueryService.handle(new GetParticipationByUserIdQuery(volunteerId));
        List<ParticipationResource> resources = ParticipationResourceFromEntityAssembler.toResourceListFromEntityList(participations);

        if (resources.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(resources,
                    messageSource.getMessage("participation.get_by_volunteer.no_data", new Object[]{volunteerId}, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok(ApiResponse.success(resources,
                messageSource.getMessage("participation.get_by_volunteer.success", null, LocaleContextHolder.getLocale())));
    }

    /**
     * Endpoint to get participation records by activity ID.
     * GET /api/v1/participations/by-activity/{activityId}
     */
    @Operation(summary = "Get participations by Activity ID", description = "Retrieves an organization's details by its unique identifier.")
    @GetMapping("by-activity/{activityId}")
    public ResponseEntity<ApiResponse<List<ParticipationResource>>> getParticipationsByActivityId(@PathVariable Long activityId) {
        List<Participation> participations = participationQueryService.handle(new GetParticipationByActivityIdQuery(activityId));
        List<ParticipationResource> resources = ParticipationResourceFromEntityAssembler.toResourceListFromEntityList(participations);

        if (resources.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(resources,
                    messageSource.getMessage("participation.get_by_activity.no_data", new Object[]{activityId}, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok(ApiResponse.success(resources,
                messageSource.getMessage("participation.get_by_activity.success", null, LocaleContextHolder.getLocale())));
    }

    /**
     * Endpoint to delete a participation record by its ID.
     * DELETE /api/v1/participations/{participationId}
     */
    @Operation(summary = "Delete a participation", description = "Deletes a participation from the system by its ID.")
    @DeleteMapping("/{participationId}")
    public ResponseEntity<ApiResponse<Void>> deleteParticipation(@PathVariable Long participationId) {
        boolean deleted = participationCommandService.handle(new DeleteParticipationCommand(participationId));
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.noContent(
                    messageSource.getMessage("participation.delete.success", null, LocaleContextHolder.getLocale())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(messageSource.getMessage("participation.delete.not_found", new Object[]{participationId}, LocaleContextHolder.getLocale()), null)
            );
        }
    }
}