package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.ActivityCommandService;
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.ActivityQueryService;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.ActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateActivityResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateActivityCommandFromResourceAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.ActivityResourceFromEntityAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateActivityCommandFromResourceAssembler;
import com.VolunTrack.demo.exception.ResourceNotFoundException;
import com.VolunTrack.demo.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for Activity-related operations.
 * This controller handles incoming HTTP requests for activity management,
 * orchestrates interaction with application services, and returns appropriate HTTP responses.
 */
@RestController
@RequestMapping(value = "/api/v1/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Activities", description = "Operations related to volunteer activities")
public class ActivityController {

    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;
    private final ActivityResourceFromEntityAssembler activityResourceFromEntityAssembler;
    private final UpdateActivityCommandFromResourceAssembler updateActivityCommandFromResourceAssembler;
    private final MessageSource messageSource;

    public ActivityController(ActivityCommandService activityCommandService,
                              ActivityQueryService activityQueryService,
                              ActivityResourceFromEntityAssembler activityResourceFromEntityAssembler,
                              UpdateActivityCommandFromResourceAssembler updateActivityCommandFromResourceAssembler,
                              MessageSource messageSource) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
        this.activityResourceFromEntityAssembler = activityResourceFromEntityAssembler;
        this.updateActivityCommandFromResourceAssembler = updateActivityCommandFromResourceAssembler;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create an activity", description = "Creates a new activity in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityResource>> createActivity(@RequestBody CreateActivityResource resource) {
        var command = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var activity = activityCommandService.handle(command);
        return activity.map(value ->
                new ResponseEntity<>(
                        ApiResponse.success(this.activityResourceFromEntityAssembler.toResourceFromEntity(value),
                                messageSource.getMessage("activity.create.success", null, LocaleContextHolder.getLocale())),
                        HttpStatus.CREATED
                )
        ).orElseGet(() ->
                ResponseEntity.badRequest().body(ApiResponse.<ActivityResource>error(
                        messageSource.getMessage("activity.create.error.invalid_data", null, LocaleContextHolder.getLocale()),
                        null))
        );
    }

    @Operation(summary = "Get all activities", description = "Retrieves a list of all registered activities.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityResource>>> getAllActivities() {
        var getAllActivitiesQuery = new GetAllActivitiesQuery();
        var activities = activityQueryService.handle(getAllActivitiesQuery);
        var activityResources = activities.stream()
                .map(this.activityResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(activityResources,
                messageSource.getMessage("activity.get_all.success", null, LocaleContextHolder.getLocale())));
    }

    @Operation(summary = "Get activity by ID", description = "Retrieves an activity's details by its unique identifier.")
    @GetMapping("/{activityId}")
    public ResponseEntity<ApiResponse<ActivityResource>> getActivityById(@PathVariable Long activityId) {
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId);
        var activity = activityQueryService.handle(getActivityByIdQuery);
        return activity.map(value ->
                ResponseEntity.ok(ApiResponse.success(this.activityResourceFromEntityAssembler.toResourceFromEntity(value),
                        messageSource.getMessage("activity.get_by_id.success", null, LocaleContextHolder.getLocale())))
        ).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("activity.not.found.by.id", new Object[]{activityId}, LocaleContextHolder.getLocale()))
        );
    }

    @Operation(summary = "Update an activity", description = "Updates the details of an existing activity.")
    @PutMapping("/{activityId}")
    public ResponseEntity<ApiResponse<ActivityResource>> updateActivity(@PathVariable Long activityId, @RequestBody UpdateActivityResource resource) {
        var command = this.updateActivityCommandFromResourceAssembler.toCommandFromResource(activityId, resource);
        var updatedActivity = activityCommandService.handle(command);
        return updatedActivity.map(value ->
                ResponseEntity.ok(ApiResponse.success(this.activityResourceFromEntityAssembler.toResourceFromEntity(value),
                        messageSource.getMessage("activity.update.success", null, LocaleContextHolder.getLocale())))
        ).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("activity.not.found.by.id", new Object[]{activityId}, LocaleContextHolder.getLocale()))
        );
    }

    @Operation(summary = "Delete an activity", description = "Deletes an activity from the system by its unique identifier.")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(@PathVariable Long activityId) {
        var command = new DeleteActivityCommand(activityId);
        activityCommandService.handle(command);
        return ResponseEntity.ok(ApiResponse.noContent(
                messageSource.getMessage("activity.delete.success", null, LocaleContextHolder.getLocale())));
    }
}