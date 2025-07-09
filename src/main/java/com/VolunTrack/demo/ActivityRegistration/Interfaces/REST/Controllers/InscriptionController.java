package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.InscriptionCommandService; // Importing the InscriptionCommandService
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.InscriptionQueryService; // Importing the InscriptionQueryService
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand; // Importing DeleteInscriptionCommand
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery; // Importing GetAllInscriptionsQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery; // Importing GetInscriptionByIdQuery
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery; // Importing GetInscriptionsByActivityIdQuery
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateInscriptionResource; // Importing CreateInscriptionResource
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.InscriptionResource; // Importing InscriptionResource for the response body
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateInscriptionResource; // Importing UpdateInscriptionResource
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateInscriptionCommandFromResourceAssembler; // For converting CreateInscriptionResource to command
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.InscriptionResourceFromEntityAssembler; // For converting Inscription entities to resources
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateInscriptionCommandFromResourceAssembler; // For converting UpdateInscriptionResource to command
import io.swagger.v3.oas.annotations.Operation; // Swagger annotation for operation descriptions
import io.swagger.v3.oas.annotations.tags.Tag; // Swagger annotation for tagging the controller
import org.springframework.http.HttpStatus; // For HTTP status codes
import org.springframework.http.MediaType; // For specifying media type in the response
import org.springframework.http.ResponseEntity; // For creating response entities
import org.springframework.web.bind.annotation.*; // For REST controller annotations

import java.util.List;
import java.util.stream.Collectors;

/**
 * The InscriptionController class is a REST controller that provides endpoints for managing volunteer inscriptions.
 * It uses **InscriptionCommandService** for command operations (creating, updating, deleting inscriptions)
 * and **InscriptionQueryService** for query operations (retrieving inscriptions).
 */
@RestController
@RequestMapping(value = "/api/v1/inscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inscriptions", description = "Operations related to enrollments in activities")
public class InscriptionController {

    private final InscriptionCommandService inscriptionCommandService; // Injected Command Service for handling commands related to inscriptions
    private final InscriptionQueryService inscriptionQueryService;   // Injected Query Service for handling queries related to inscriptions

    /**
     * Constructor to inject the required command and query services.
     * 
     * @param inscriptionCommandService - The service for handling commands related to inscriptions.
     * @param inscriptionQueryService - The service for handling queries related to inscriptions.
     */
    public InscriptionController(InscriptionCommandService inscriptionCommandService, InscriptionQueryService inscriptionQueryService) {
        this.inscriptionCommandService = inscriptionCommandService;
        this.inscriptionQueryService = inscriptionQueryService;
    }

    /**
     * Handles the request to create a new inscription.
     * 
     * @param resource - The resource containing data for creating the inscription.
     * @return A ResponseEntity with the created inscription's resource or a bad request response if creation fails.
     */
    @Operation(summary = "Create a inscription", description = "Creates a new inscription in the system.")
    @PostMapping
    public ResponseEntity<InscriptionResource> createInscription(@RequestBody CreateInscriptionResource resource) {
        var command = CreateInscriptionCommandFromResourceAssembler.toCommandFromResource(resource); // Convert resource to command
        try {
            var inscription = inscriptionCommandService.handle(command); // Use command service to handle inscription creation
            return inscription.map(value -> 
                    new ResponseEntity<>(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED) // Return the created inscription resource
            ).orElseGet(() -> ResponseEntity.badRequest().build()); // Return bad request if creation fails
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build(); // Handle IllegalArgumentException and return bad request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // Return conflict status if state is not valid
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }

    /**
     * Handles the request to get all inscriptions.
     * 
     * @return A ResponseEntity containing a list of all inscriptions as resources.
     */
    @Operation(summary = "Get all inscriptions", description = "Retrieves a list of all registered inscriptions.")
    @GetMapping
    public ResponseEntity<List<InscriptionResource>> getAllInscriptions() {
        var getAllInscriptionsQuery = new GetAllInscriptionsQuery(); // Create query to get all inscriptions
        var inscriptions = inscriptionQueryService.handle(getAllInscriptionsQuery); // Use query service to get all inscriptions
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity) // Convert entities to resources
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscriptionResources); // Return the list of inscriptions
    }

    /**
     * Handles the request to get an inscription by its ID.
     * 
     * @param inscriptionId - The ID of the inscription to fetch.
     * @return A ResponseEntity containing the inscription resource if found, or a not found response if not found.
     */
    @Operation(summary = "Get inscription by ID", description = "Retrieves an inscription's details by its unique identifier.")
    @GetMapping("/{inscriptionId}")
    public ResponseEntity<InscriptionResource> getInscriptionById(@PathVariable Long inscriptionId) {
        var getInscriptionByIdQuery = new GetInscriptionByIdQuery(inscriptionId); // Create query to get inscription by ID
        var inscription = inscriptionQueryService.handle(getInscriptionByIdQuery); // Use query service to get inscription by ID
        return inscription.map(value -> 
                ResponseEntity.ok(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value)) // Return inscription resource if found
        ).orElseGet(() -> ResponseEntity.notFound().build()); // Return not found response if inscription is not found
    }

    /**
     * Handles the request to get inscriptions by a specific activity ID.
     * 
     * @param activityId - The ID of the activity to get inscriptions for.
     * @return A ResponseEntity containing the list of inscriptions related to the activity.
     */
    @Operation(summary = "Get inscription by activity ID", description = "Retrieves an inscription's details by activity ID.")
    @GetMapping("/byActivity/{activityId}")
    public ResponseEntity<List<InscriptionResource>> getInscriptionsByActivityId(@PathVariable Long activityId) {
        var query = new GetInscriptionsByActivityIdQuery(activityId); // Create query to get inscriptions by activity ID
        var inscriptions = inscriptionQueryService.handle(query); // Use query service to get inscriptions for the activity
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity) // Convert entities to resources
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscriptionResources); // Return the list of inscriptions related to the activity
    }

    /**
     * Handles the request to update an existing inscription.
     * 
     * @param inscriptionId - The ID of the inscription to update.
     * @param resource - The resource containing the updated inscription data.
     * @return A ResponseEntity containing the updated inscription resource, or a not found response if the inscription was not found.
     */
    @Operation(summary = "Update an inscription", description = "Updates the details of an existing inscription.")
    @PutMapping("/{inscriptionId}")
    public ResponseEntity<InscriptionResource> updateInscription(@PathVariable Long inscriptionId, @RequestBody UpdateInscriptionResource resource) {
        var command = UpdateInscriptionCommandFromResourceAssembler.toCommandFromResource(inscriptionId, resource); // Convert resource to command
        try {
            var updatedInscription = inscriptionCommandService.handle(command); // Use command service to handle inscription update
            return updatedInscription.map(value -> 
                    ResponseEntity.ok(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value)) // Return updated inscription resource
            ).orElseGet(() -> ResponseEntity.notFound().build()); // Return not found response if inscription not found
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build(); // Handle exceptions and return bad request
        }
    }

    /**
     * Handles the request to delete an inscription by its ID.
     * 
     * @param inscriptionId - The ID of the inscription to delete.
     * @return A ResponseEntity indicating the result of the deletion.
     */
    @Operation(summary = "Delete an inscription", description = "Deletes an inscription from the system by its unique identifier.")
    @DeleteMapping("/{inscriptionId}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long inscriptionId) {
        try {
            var command = new DeleteInscriptionCommand(inscriptionId); // Create command for deletion
            inscriptionCommandService.handle(command); // Use command service to delete inscription
            return ResponseEntity.noContent().build(); // Return no content response if deletion is successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Return not found response if inscription doesn't exist
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // Return internal server error if state is invalid
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
}
