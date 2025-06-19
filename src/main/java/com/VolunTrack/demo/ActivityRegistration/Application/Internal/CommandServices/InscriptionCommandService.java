package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IInscriptionService;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionCommandService implements IInscriptionService {

    private final IInscriptionRepository inscriptionRepository;
    private final IVolunteerRepository volunteerRepository;
    private final IActivityRepository activityRepository;
    private final INotificationCommandService notificationCommandService;

    public InscriptionCommandService(IInscriptionRepository inscriptionRepository,
                                     IVolunteerRepository volunteerRepository,
                                     IActivityRepository activityRepository,
                                     INotificationCommandService notificationCommandService) {
        this.inscriptionRepository = inscriptionRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
        this.notificationCommandService = notificationCommandService;
    }

    @Override
    @Transactional
    public Optional<Inscription> handle(CreateInscriptionCommand command) {

        if (!volunteerRepository.existsById(command.voluntarioId())) {
            throw new IllegalArgumentException("Volunteer with ID " + command.voluntarioId() + " does not exist.");
        }

        Activity activity = activityRepository.findById(command.actividadId())
                .orElseThrow(() -> new IllegalArgumentException("Activity with ID " + command.actividadId() + " does not exist."));

        if (inscriptionRepository.findByVoluntarioIdAndActividadId(command.voluntarioId(), command.actividadId()).isPresent()) {
            throw new IllegalArgumentException("Volunteer " + command.voluntarioId() + " is already enrolled in activity " + command.actividadId());
        }

        if (!activity.tryIncrementInscriptionsActuales()) {
            throw new IllegalStateException("No available slots for activity with ID " + command.actividadId());
        }

        Inscription inscription = new Inscription(
                command.voluntarioId(),
                command.estado(),
                command.fecha(),
                command.actividadId()
        );

        activityRepository.save(activity);

        Inscription savedInscription = inscriptionRepository.save(inscription);


        try {
            CreateNotificationCommand volunteerNotification = new CreateNotificationCommand(
                    NotificationType.JOINED_ACTIVITY,
                    savedInscription.getVoluntarioId(),
                    RecipientType.VOLUNTEER
            );
            notificationCommandService.handle(volunteerNotification);
        } catch (Exception e) {
            System.err.println("Error creating joined-activity notification for volunteer " + savedInscription.getVoluntarioId() + ": " + e.getMessage());
        }


        try {
            CreateNotificationCommand organizationNotification = new CreateNotificationCommand(
                    NotificationType.VOLUNTEER_JOINED,
                    (long) activity.getOrganizacion_id(),
                    RecipientType.ORGANIZATION
            );
            notificationCommandService.handle(organizationNotification);
        } catch (Exception e) {
            System.err.println("Error creating volunteer-joined notification for organization " + activity.getOrganizacion_id() + ": " + e.getMessage());
        }

        return Optional.of(savedInscription);
    }

    @Override
    public Optional<Inscription> handle(UpdateInscriptionCommand command) {
        return inscriptionRepository.findById(command.inscriptionId()).map(inscription -> {
            inscription.setVoluntarioId(command.voluntarioId());
            inscription.setEstado(command.estado());
            inscription.setFecha(command.fecha());
            inscription.setActividadId(command.actividadId());
            return inscriptionRepository.save(inscription);
        });
    }

    @Override
    @Transactional
    public void handle(DeleteInscriptionCommand command) {
        Inscription inscriptionToDelete = inscriptionRepository.findById(command.inscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Enrollment with ID " + command.inscriptionId() + " not found."));

        Activity activity = activityRepository.findById(inscriptionToDelete.getActividadId())
                .orElseThrow(() -> new IllegalStateException("Associated activity not found for inscription ID " + inscriptionToDelete.getInscription_id()));

        activity.tryDecrementInscriptionsActuales();
        activityRepository.save(activity);

        inscriptionRepository.deleteById(command.inscriptionId());
    }

    @Override
    public List<Inscription> handle(GetAllInscriptionsQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }

    @Override
    public Optional<Inscription> handle(GetInscriptionByIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }

    @Override
    public List<Inscription> handle(GetInscriptionsByActivityIdQuery query) {
        throw new UnsupportedOperationException("Query operations should be handled by InscriptionQueryService");
    }
}