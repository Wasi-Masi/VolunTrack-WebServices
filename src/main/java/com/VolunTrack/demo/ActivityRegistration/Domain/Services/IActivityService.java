package com.VolunTrack.demo.ActivityRegistration.Domain.Services;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery;

import java.util.List;
import java.util.Optional;

// This is an interface for the Application Services layer, combining both command and query operations.
public interface IActivityService {
    Optional<Activity> handle(CreateActivityCommand command);
    Optional<Activity> handle(UpdateActivityCommand command);
    void handle(DeleteActivityCommand command);
    List<Activity> handle(GetAllActivitiesQuery query);
    Optional<Activity> handle(GetActivityByIdQuery query);
}