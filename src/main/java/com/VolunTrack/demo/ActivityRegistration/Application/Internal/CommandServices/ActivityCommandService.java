package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IActivityService;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityCommandService implements IActivityService {

    private final IActivityRepository activityRepository;
    private final INotificationCommandService notificationCommandService;

    public ActivityCommandService(IActivityRepository activityRepository,
                                  INotificationCommandService notificationCommandService) {
        this.activityRepository = activityRepository;
        this.notificationCommandService = notificationCommandService;
    }

    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        if (activityRepository.findByTitulo(command.titulo()).isPresent()) {
            throw new IllegalArgumentException("Activity with title " + command.titulo() + " already exists.");
        }
        Activity activity = new Activity(
                command.fecha(),
                command.horainicio(),
                command.horaFin(),
                command.titulo(),
                command.descripcion(),
                command.instrucciones(),
                command.proposito(),
                command.cupos(),
                command.ubicacion(),
                command.estado(),
                command.organizacionId()
        );
        Activity savedActivity = activityRepository.save(activity);


        try {
            CreateNotificationCommand notificationCommand = new CreateNotificationCommand(
                    NotificationType.NEW_ACTIVITY,
                    (long) savedActivity.getOrganizacion_id(),
                    RecipientType.ORGANIZATION
            );
            notificationCommandService.handle(notificationCommand);
        } catch (Exception e) {
            System.err.println("Error creating new-activity notification for organization " + savedActivity.getOrganizacion_id() + ": " + e.getMessage());
        }

        return Optional.of(savedActivity);
    }

    @Override
    public Optional<Activity> handle(UpdateActivityCommand command) {
        return activityRepository.findById(command.actividadId()).map(activity -> {
            activity.setFecha(command.fecha());
            activity.setHorainicio(command.horainicio());
            activity.setHoraFin(command.horaFin());
            activity.setTitulo(command.titulo());
            activity.setDescripcion(command.descripcion());
            activity.setInstrucciones(command.instrucciones());
            activity.setProposito(command.proposito());
            activity.setCupos(command.cupos());
            activity.setUbicacion(command.ubicacion());
            activity.setEstado(command.estado());
            activity.setOrganizacion_id(command.organizacionId());
            return activityRepository.save(activity);
        });
    }

    @Override
    public void handle(DeleteActivityCommand command) {
        if (!activityRepository.existsById(command.actividadId())) {
            throw new IllegalArgumentException("Activity with ID " + command.actividadId() + " not found.");
        }
        activityRepository.deleteById(command.actividadId());
    }

    @Override
    public List<Activity> handle(GetAllActivitiesQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }

    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }
}