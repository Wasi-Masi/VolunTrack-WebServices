package com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionQueryService implements com.VolunTrack.demo.ActivityRegistration.Application.Services.IInscriptionService {

    private final IInscriptionRepository inscriptionRepository;

    public InscriptionQueryService(IInscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public List<Inscription> handle(GetAllInscriptionsQuery query) {
        return inscriptionRepository.findAll();
    }

    @Override
    public Optional<Inscription> handle(GetInscriptionByIdQuery query) {
        return inscriptionRepository.findById(query.inscriptionId());
    }

    // Query service does not handle commands, throw UnsupportedOperationException
    @Override
    public Optional<Inscription> handle(CreateInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }

    @Override
    public Optional<Inscription> handle(UpdateInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }

    @Override
    public void handle(DeleteInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }
}