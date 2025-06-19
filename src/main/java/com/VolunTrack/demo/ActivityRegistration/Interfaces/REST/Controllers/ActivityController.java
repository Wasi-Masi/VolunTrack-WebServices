package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.ActivityCommandService; // New import
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.ActivityQueryService; // New import
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

@RestController
@RequestMapping(value = "/api/v1/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Activities", description = "Operations related to volunteer activities")
public class ActivityController {

    private final ActivityCommandService activityCommandService; // Injected Command Service
    private final ActivityQueryService activityQueryService;     // Injected Query Service

    public ActivityController(ActivityCommandService activityCommandService, ActivityQueryService activityQueryService) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
    }

    @Operation(summary = "Create an activity", description = "Creates a new activity in the system.")
    @PostMapping
    public ResponseEntity<ActivityResource> createActivity(@RequestBody CreateActivityResource resource) {
        var command = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var activity = activityCommandService.handle(command); // Use command service
        return activity.map(value ->
                new ResponseEntity<>(ActivityResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
        ).orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @Operation(summary = "Get all activities", description = "Retrieves a list of all registered activities.")
    @GetMapping
    public ResponseEntity<List<ActivityResource>> getAllActivities() {
        var getAllActivitiesQuery = new GetAllActivitiesQuery();
        var activities = activityQueryService.handle(getAllActivitiesQuery); // Use query service
        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityResources);
    }

    @Operation(summary = "Get activity by ID", description = "Retrieves an activity's details by its unique identifier.")
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResource> getActivityById(@PathVariable Long activityId) {
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId);
        var activity = activityQueryService.handle(getActivityByIdQuery); // Use query service
        return activity.map(value ->
                ResponseEntity.ok(ActivityResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an activity", description = "Updates the details of an existing activity.")
    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResource> updateActivity(@PathVariable Long activityId, @RequestBody UpdateActivityResource resource) {
        var command = UpdateActivityCommandFromResourceAssembler.toCommandFromResource(activityId, resource);
        var updatedActivity = activityCommandService.handle(command); // Use command service
        return updatedActivity.map(value ->
                ResponseEntity.ok(ActivityResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an activity", description = "Deletes an activity from the system by its unique identifier.")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long activityId) {
        try {
            var command = new com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand(activityId);
            activityCommandService.handle(command); // Use command service
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}