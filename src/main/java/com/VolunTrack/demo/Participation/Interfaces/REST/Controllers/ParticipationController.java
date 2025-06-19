package com.VolunTrack.demo.Participation.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Participation.Application.Internal.CommandServices.ParticipationCommandService;
import com.VolunTrack.demo.Participation.Application.Internal.QueryServices.ParticipationQueryService;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByActivityIdQuery;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByUserIdQuery;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateParticipationResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.ParticipationResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CreateParticipationCommandFromResourceAssembler;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.ParticipationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Participation records.
 */
@RestController
@RequestMapping(value = "/api/v1/participations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Participations", description = "Participation Management Endpoints")
public class ParticipationController {

    private final ParticipationCommandService participationCommandService;
    private final ParticipationQueryService participationQueryService;

    public ParticipationController(ParticipationCommandService participationCommandService, ParticipationQueryService participationQueryService) {
        this.participationCommandService = participationCommandService;
        this.participationQueryService = participationQueryService;
    }

    /**
     * Endpoint to create a new participation record.
     * POST /api/v1/participations
     */
    @PostMapping
    public ResponseEntity<ParticipationResource> createParticipation(@RequestBody @Valid CreateParticipationResource resource) {
        Optional<Participation> participation = participationCommandService.handle(
                CreateParticipationCommandFromResourceAssembler.toCommandFromResource(resource));

        return participation.map(p -> new ResponseEntity<>(ParticipationResourceFromEntityAssembler.toResourceFromEntity(p), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint to get participation records by volunteer ID.
     * GET /api/v1/participations/by-volunteer/{volunteerId}
     */
    @GetMapping("by-volunteer/{volunteerId}")
    public ResponseEntity<List<ParticipationResource>> getParticipationsByVolunteerId(@PathVariable Long volunteerId) {
        List<Participation> participations = participationQueryService.handle(new GetParticipationByUserIdQuery(volunteerId));
        if (participations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ParticipationResource> resources = ParticipationResourceFromEntityAssembler.toResourceListFromEntityList(participations);
        return ResponseEntity.ok(resources);
    }

    /**
     * Endpoint to get participation records by activity ID.
     * GET /api/v1/participations/by-activity/{activityId}
     */
    @GetMapping("by-activity/{activityId}")
    public ResponseEntity<List<ParticipationResource>> getParticipationsByActivityId(@PathVariable Long activityId) {
        List<Participation> participations = participationQueryService.handle(new GetParticipationByActivityIdQuery(activityId));
        if (participations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ParticipationResource> resources = ParticipationResourceFromEntityAssembler.toResourceListFromEntityList(participations);
        return ResponseEntity.ok(resources);
    }


}