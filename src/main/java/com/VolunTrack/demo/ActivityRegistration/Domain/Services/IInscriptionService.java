package com.VolunTrack.demo.ActivityRegistration.Domain.Services;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription; // Importing the Inscription aggregate
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand; // Importing CreateInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand; // Importing DeleteInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand; // Importing UpdateInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery; // Importing GetAllInscriptionsQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery; // Importing GetInscriptionByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery; // Importing the new GetInscriptionsByActivityIdQuery

import java.util.List;
import java.util.Optional;

/**
 * The IInscriptionService interface defines the contract for the application services layer
 * that handles both **command** and **query** operations related to volunteer inscriptions.
 * This service layer ensures that all business rules are followed and is part of the CQRS (Command Query Responsibility Segregation) architecture.
 */
public interface IInscriptionService {

    /**
     * Handles the creation of a new inscription.
     *
     * @param command - The command containing the data to create a new inscription.
     * @return An Optional containing the created inscription if successful, or empty if not.
     */
    Optional<Inscription> handle(CreateInscriptionCommand command);

    /**
     * Handles the updating of an existing inscription.
     *
     * @param command - The command containing the data to update an inscription.
     * @return An Optional containing the updated inscription if successful, or empty if not found.
     */
    Optional<Inscription> handle(UpdateInscriptionCommand command);

    /**
     * Handles the deletion of an inscription.
     *
     * @param command - The command containing the ID of the inscription to delete.
     */
    void handle(DeleteInscriptionCommand command);

    /**
     * Handles the retrieval of all inscriptions.
     *
     * @param query - The query to get all inscriptions.
     * @return A list of all inscriptions in the system.
     */
    List<Inscription> handle(GetAllInscriptionsQuery query);

    /**
     * Handles the retrieval of an inscription by its unique ID.
     *
     * @param query - The query containing the ID of the inscription to fetch.
     * @return An Optional containing the inscription if found, or empty if not found.
     */
    Optional<Inscription> handle(GetInscriptionByIdQuery query);

    /**
     * Handles the retrieval of inscriptions by a specific activity ID.
     *
     * @param query - The query containing the ID of the activity to get the inscriptions for.
     * @return A list of inscriptions related to the given activity.
     */
    List<Inscription> handle(GetInscriptionsByActivityIdQuery query); // NEW implementation
}
