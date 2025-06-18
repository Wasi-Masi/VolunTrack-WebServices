package com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionCommandService implements com.VolunTrack.demo.ActivityRegistration.Application.Services.IInscriptionService {

    private final IInscriptionRepository inscriptionRepository;
    private final IVolunteerRepository volunteerRepository;
    private final IActivityRepository activityRepository;

    public InscriptionCommandService(IInscriptionRepository inscriptionRepository,
                                     IVolunteerRepository volunteerRepository,
                                     IActivityRepository activityRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public Optional<Inscription> handle(CreateInscriptionCommand command) {

        if (!volunteerRepository.existsById(command.voluntarioId())) {
            throw new IllegalArgumentException("Volunteer with ID " + command.voluntarioId() + " does not exist.");
        }


        if (!activityRepository.existsById(command.actividadId())) {
            throw new IllegalArgumentException("Activity with ID " + command.actividadId() + " does not exist.");
        }


        if (inscriptionRepository.findByVoluntarioIdAndActividadId(command.voluntarioId(), command.actividadId()).isPresent()) {
            throw new IllegalArgumentException("Volunteer " + command.voluntarioId() + " is already enrolled in activity " + command.actividadId());
        }


        Inscription inscription = new Inscription(
                command.voluntarioId(),
                command.estado(),
                command.fecha(),
                command.actividadId()
        );
        return Optional.of(inscriptionRepository.save(inscription));
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
    public void handle(DeleteInscriptionCommand command) {
        if (!inscriptionRepository.existsById(command.inscriptionId())) {
            throw new IllegalArgumentException("Enrollment with ID " + command.inscriptionId() + " not found.");
        }
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
}