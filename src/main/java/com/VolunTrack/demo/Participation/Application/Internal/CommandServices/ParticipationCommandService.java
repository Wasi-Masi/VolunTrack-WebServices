package com.VolunTrack.demo.Participation.Application.Internal.CommandServices;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Commands.CreateParticipationCommand;
import com.VolunTrack.demo.Participation.Domain.Model.Commands.DeleteParticipationCommand; // <-- ¡NUEVO!
import com.VolunTrack.demo.Participation.Domain.Services.IParticipationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application Command Service for Participation.
 * Handles the execution of commands related to Participation, delegating to the domain service.
 */
@Service
public class ParticipationCommandService {

    private final IParticipationService participationService;

    public ParticipationCommandService(IParticipationService participationService) {
        this.participationService = participationService;
    }

    /**
     * Handles the CreateParticipationCommand.
     * Delegates the creation of a participation record to the domain service.
     *
     * @param command The command containing the details for the new participation.
     * @return An Optional containing the created Participation if successful, or empty otherwise.
     */
    public Optional<Participation> handle(CreateParticipationCommand command) {
        return participationService.createParticipation(
                command.volunteerId(),
                command.activityId(),
                command.initialStatus()
        );
    }

    /**
     * Handles the DeleteParticipationCommand.
     * Delegates the deletion of a participation record to the domain service.
     *
     * @param command The command containing the ID of the participation to delete.
     * @return True if the participation was deleted successfully, false otherwise.
     */
    public boolean handle(DeleteParticipationCommand command) { // <-- ¡NUEVO MÉTODO!
        return participationService.deleteParticipation(command.participationId());
    }
}