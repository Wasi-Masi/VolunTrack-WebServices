package com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription; // Importing the Inscription aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand; // Importing CreateInscriptionCommand for IInscriptionService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand; // Importing DeleteInscriptionCommand for IInscriptionService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand; // Importing UpdateInscriptionCommand for IInscriptionService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery; // Importing GetAllInscriptionsQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery; // Importing GetInscriptionByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery; // Importing the new query GetInscriptionsByActivityIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository; // Importing the Inscription repository interface
import com.VolunTrack.demo.ActivityRegistration.Domain.Services.IInscriptionService; // Importing the Inscription service interface
import org.springframework.stereotype.Service; // Spring's annotation for marking this class as a service

import java.util.List;
import java.util.Optional;

/**
 * The InscriptionQueryService class handles query operations related to volunteer inscriptions,
 * such as retrieving all inscriptions, getting inscriptions by ID, and getting inscriptions for a specific activity.
 * It is part of the read model in a CQRS (Command Query Responsibility Segregation) architecture.
 * This service interacts with the repository to retrieve data based on the provided queries.
 */
@Service
public class InscriptionQueryService implements IInscriptionService {

    private final IInscriptionRepository inscriptionRepository; // Repository to interact with inscription data

    /**
     * Constructor to inject the inscription repository dependency.
     *
     * @param inscriptionRepository - The repository for managing inscription data.
     */
    public InscriptionQueryService(IInscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    /**
     * Handles the retrieval of all inscriptions from the repository.
     * This method handles the query to fetch all inscriptions in the system.
     *
     * @param query - The query to get all inscriptions.
     * @return A list of all inscriptions.
     */
    @Override
    public List<Inscription> handle(GetAllInscriptionsQuery query) {
        return inscriptionRepository.findAll(); // Retrieve all inscriptions from the repository
    }

    /**
     * Handles the retrieval of a specific inscription by its ID.
     * This method handles the query to fetch a single inscription based on its unique ID.
     *
     * @param query - The query containing the ID of the inscription to fetch.
     * @return An Optional containing the inscription if found, or empty if not found.
     */
    @Override
    public Optional<Inscription> handle(GetInscriptionByIdQuery query) {
        return inscriptionRepository.findById(query.inscriptionId()); // Retrieve inscription by ID from the repository
    }

    /**
     * Handles the retrieval of inscriptions by a specific activity ID.
     * This method handles the query to fetch all inscriptions related to a particular activity.
     *
     * @param query - The query containing the ID of the activity to get the inscriptions for.
     * @return A list of inscriptions related to the given activity.
     */
    @Override
    public List<Inscription> handle(GetInscriptionsByActivityIdQuery query) { // NEW implementation
        return inscriptionRepository.findByActividadId(query.activityId()); // Retrieve inscriptions for a specific activity
    }

    /**
     * This method handles command operations, but these operations should be handled by the InscriptionCommandService.
     * Therefore, this method throws UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to create a new inscription.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public Optional<Inscription> handle(CreateInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }

    /**
     * This method handles command operations, but these operations should be handled by the InscriptionCommandService.
     * Therefore, this method throws UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to update an existing inscription.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public Optional<Inscription> handle(UpdateInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }

    /**
     * This method handles command operations, but these operations should be handled by the InscriptionCommandService.
     * Therefore, this method throws UnsupportedOperationException to ensure that command operations are delegated elsewhere.
     *
     * @param command - The command to delete an inscription.
     * @throws UnsupportedOperationException - Indicates that this method should not be used in the query service.
     */
    @Override
    public void handle(DeleteInscriptionCommand command) {
        throw new UnsupportedOperationException("Command operations should be handled by InscriptionCommandService");
    }
}
