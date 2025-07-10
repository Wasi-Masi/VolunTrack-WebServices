package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.ActivityCommandService;
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.ActivityQueryService;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateActivityCommandFromResourceAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.ActivityResourceFromEntityAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateActivityCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The ActivityController class provides the RESTful API for managing activities in the system.
 * It uses **ActivityCommandService** for command operations (creating, updating, deleting activities)
 * and **ActivityQueryService** for query operations (retrieving activities).
 */
@RestController
@RequestMapping(value = "/api/v1/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Activities", description = "Operations related to volunteer activities")
public class ActivityController {

    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;
    private final ActivityResourceFromEntityAssembler activityResourceFromEntityAssembler; // Se inyecta porque sus métodos son no-estáticos
    private final UpdateActivityCommandFromResourceAssembler updateActivityCommandFromResourceAssembler; // Se inyecta porque sus métodos son no-estáticos
    // CreateActivityCommandFromResourceAssembler NO se inyecta porque su método 'toCommandFromResource' es estático.

    /**
     * Constructor to inject the required command and query services, and the assemblers.
     */
    public ActivityController(ActivityCommandService activityCommandService,
                              ActivityQueryService activityQueryService,
                              ActivityResourceFromEntityAssembler activityResourceFromEntityAssembler,
                              UpdateActivityCommandFromResourceAssembler updateActivityCommandFromResourceAssembler) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
        this.activityResourceFromEntityAssembler = activityResourceFromEntityAssembler;
        this.updateActivityCommandFromResourceAssembler = updateActivityCommandFromResourceAssembler;
    }

    /**
     * Handles the request to create a new activity.
     */
    @Operation(summary = "Create an activity", description = "Creates a new activity in the system.")
    @PostMapping
    public ResponseEntity<ActivityResource> createActivity(@RequestBody CreateActivityResource resource) {
        // Llamada estática, ya que el método toCommandFromResource en CreateActivityCommandFromResourceAssembler es estático.
        var command = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var activity = activityCommandService.handle(command);
        return activity.map(value ->
                new ResponseEntity<>(this.activityResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Handles the request to get all activities.
     */
    @Operation(summary = "Get all activities", description = "Retrieves a list of all registered activities.")
    @GetMapping
    public ResponseEntity<List<ActivityResource>> getAllActivities() {
        var getAllActivitiesQuery = new GetAllActivitiesQuery();
        var activities = activityQueryService.handle(getAllActivitiesQuery);
        var activityResources = activities.stream()
                // Se usa la instancia inyectada para llamar a métodos no-estáticos.
                .map(this.activityResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityResources);
    }

    /**
     * Handles the request to get an activity by its ID.
     */
    @Operation(summary = "Get activity by ID", description = "Retrieves an activity's details by its unique identifier.")
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResource> getActivityById(@PathVariable Long activityId) {
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId);
        var activity = activityQueryService.handle(getActivityByIdQuery);
        return activity.map(value ->
                // Se usa la instancia inyectada para llamar a métodos no-estáticos.
                ResponseEntity.ok(this.activityResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the request to update an existing activity.
     */
    @Operation(summary = "Update an activity", description = "Updates the details of an existing activity.")
    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResource> updateActivity(@PathVariable Long activityId, @RequestBody UpdateActivityResource resource) {
        // Se usa la instancia inyectada para llamar a métodos no-estáticos.
        var command = this.updateActivityCommandFromResourceAssembler.toCommandFromResource(activityId, resource);
        var updatedActivity = activityCommandService.handle(command);
        return updatedActivity.map(value ->
                // Se usa la instancia inyectada para llamar a métodos no-estáticos.
                ResponseEntity.ok(this.activityResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Handles the request to delete an activity by its ID.
     */
    @Operation(summary = "Delete an activity", description = "Deletes an activity from the system by its unique identifier.")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long activityId) {
        try {
            var command = new com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand(activityId);
            activityCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}