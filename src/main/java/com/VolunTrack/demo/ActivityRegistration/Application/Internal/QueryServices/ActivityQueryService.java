package com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand; // Added for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand; // Added for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand; // Added for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IActivityService; // Changed import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityQueryService implements IActivityService { // Implements IActivityService

    private final IActivityRepository activityRepository;

    public ActivityQueryService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> handle(GetAllActivitiesQuery query) {
        return activityRepository.findAll();
    }

    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        return activityRepository.findById(query.actividadId());
    }

    // Methods from IActivityService now handled directly in CommandService or here if appropriate
    // For a clear separation, these command methods will throw UnsupportedOperationException
    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }

    @Override
    public Optional<Activity> handle(UpdateActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }

    @Override
    public void handle(DeleteActivityCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by ActivityCommandService");
    }
}