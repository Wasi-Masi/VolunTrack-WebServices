package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateActivityCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IActivityService; // Changed import
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllActivitiesQuery; // Added for IActivityService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetActivityByIdQuery; // Added for IActivityService
import org.springframework.stereotype.Service;

import java.util.List; // Added for IActivityService
import java.util.Optional;

@Service
public class ActivityCommandService implements IActivityService { // Implements IActivityService

    private final IActivityRepository activityRepository;

    public ActivityCommandService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Optional<Activity> handle(CreateActivityCommand command) {
        // You might add business rules here before saving
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
        return Optional.of(activityRepository.save(activity));
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

    // Methods from IActivityService now handled directly in QueryService or here if appropriate
    // For a clear separation, these query methods will be implemented in ActivityQueryService
    @Override
    public List<Activity> handle(GetAllActivitiesQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }

    @Override
    public Optional<Activity> handle(GetActivityByIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by ActivityQueryService");
    }
}