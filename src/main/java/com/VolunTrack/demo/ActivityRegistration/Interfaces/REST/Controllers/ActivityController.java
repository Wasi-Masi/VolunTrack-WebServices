package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.ActivityCommandService; // Importing the ActivityCommandService
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.ActivityQueryService; // Importing the ActivityQueryService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery; // Importing the GetAllActivitiesQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery; // Importing the GetActivityByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource; // Importing CreateActivityResource for request body
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource; // Importing ActivityResource for response body
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource; // Importing UpdateActivityResource for request body
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateActivityCommandFromResourceAssembler; // Importing the assembler to convert resource to command
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.ActivityResourceFromEntityAssembler; // Importing assembler to convert entity to resource
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateActivityCommandFromResourceAssembler; // Importing assembler to convert update resource to command
import io.swagger.v3.oas.annotations.Operation; // For Swagger annotations to document API
import io.swagger.v3.oas.annotations.tags.Tag; // For Swagger tags to organize API operations
import org.springframework.http.HttpStatus; // For setting HTTP status codes
import org.springframework.http.MediaType; // For specifying the response media type
import org.springframework.http.ResponseEntity; // For building response entities
import org.springframework.web.bind.annotation.*; // For REST controller annotations

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

    private final ActivityCommandService activityCommandService; // Injected Command Service for handling commands
    private final ActivityQueryService activityQueryService;     // Injected Query Service for handling queries

    /**
     * Constructor to inject the required command and query services.
     * 
     * @param activityCommandService - The service for handling commands related to activities.
     * @param activityQueryService - The service for handling queries related to activities.
     */
    public ActivityController(ActivityCommandService activityCommandService, ActivityQueryService activityQueryService) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
    }

    /**
     * Handles the request to create a new activity.
     * 
     * @param resource - The resource containing data for creating the activity.
     * @return A ResponseEntity with the created activity's resource or a bad request response.
     */
    @Operation(summary = "Create an activity", description = "Creates a new activity in the system.")
    @PostMapping
    public ResponseEntity<ActivityResource> createActivity(@RequestBody CreateActivityResource resource) {
        var command = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource); // Convert resource to command
        var activity = activityCommandService.handle(command); // Use command service to handle activity creation
        return activity.map(value -> 
                new ResponseEntity<>(ActivityResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED) // Return the created activity resource
        ).orElseGet(() -> ResponseEntity.badRequest().build()); // Return bad request if creation failed
    }

    /**
     * Handles the request to get all activities.
     * 
     * @return A ResponseEntity containing a list of all activities as resources.
     */
    @Operation(summary = "Get all activities", description = "Retrieves a list of all registered activities.")
    @GetMapping
    public ResponseEntity<List<ActivityResource>> getAllActivities() {
        var getAllActivitiesQuery = new GetAllActivitiesQuery(); // Create query to get all activities
        var activities = activityQueryService.handle(getAllActivitiesQuery); // Use query service to get all activities
        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity) // Convert entities to resources
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityResources); // Return the list of activities
    }

    /**
     * Handles the request to get an activity by its ID.
     * 
     * @param activityId - The ID of the activity to fetch.
     * @return A ResponseEntity containing the activity resource if found, or a not found response.
     */
    @Operation(summary = "Get activity by ID", description = "Retrieves an activity's details by its unique identifier.")
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResource> getActivityById(@PathVariable Long activityId) {
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId); // Create query to get activity by ID
        var activity = activityQueryService.handle(getActivityByIdQuery); // Use query service to get activity by ID
        return activity.map(value -> 
                ResponseEntity.ok(ActivityResourceFromEntityAssembler.toResourceFromEntity(value)) // Return activity resource if found
        ).orElseGet(() -> ResponseEntity.notFound().build()); // Return not found response if activity is not found
    }

    /**
     * Handles the request to update an existing activity.
     * 
     * @param activityId - The ID of the activity to update.
     * @param resource - The resource containing the updated activity data.
     * @return A ResponseEntity containing the updated activity resource, or a not found response if the activity was not found.
     */
    @Operation(summary = "Update an activity", description = "Updates the details of an existing activity.")
    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResource> updateActivity(@PathVariable Long activityId, @RequestBody UpdateActivityResource resource) {
        var command = UpdateActivityCommandFromResourceAssembler.toCommandFromResource(activityId, resource); // Convert resource to command
        var updatedActivity = activityCommandService.handle(command); // Use command service to handle activity update
        return updatedActivity.map(value -> 
                ResponseEntity.ok(ActivityResourceFromEntityAssembler.toResourceFromEntity(value)) // Return updated activity resource
        ).orElseGet(() -> ResponseEntity.notFound().build()); // Return not found response if activity not found
    }

    /**
     * Handles the request to delete an activity by its ID.
     * 
     * @param activityId - The ID of the activity to delete.
     * @return A ResponseEntity indicating the result of the deletion.
     */
    @Operation(summary = "Delete an activity", description = "Deletes an activity from the system by its unique identifier.")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long activityId) {
        try {
            var command = new com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand(activityId); // Create command for deletion
            activityCommandService.handle(command); // Use command service to delete activity
            return ResponseEntity.noContent().build(); // Return no content response if deletion is successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Return not found response if activity doesn't exist
        }
    }
}
