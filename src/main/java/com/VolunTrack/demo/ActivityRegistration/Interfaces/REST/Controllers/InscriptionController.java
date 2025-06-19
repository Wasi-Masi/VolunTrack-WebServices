package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Controllers;

import com.VolunTrack.demo.ActivityRegistration.Application.Internal.CommandServices.InscriptionCommandService;
import com.VolunTrack.demo.ActivityRegistration.Application.Internal.QueryServices.InscriptionQueryService;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.CreateInscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.InscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources.UpdateInscriptionResource;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.CreateInscriptionCommandFromResourceAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.InscriptionResourceFromEntityAssembler;
import com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Transform.UpdateInscriptionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/inscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inscriptions", description = "Operations related to enrollments in activities")
public class InscriptionController {

    private final InscriptionCommandService inscriptionCommandService;
    private final InscriptionQueryService inscriptionQueryService;

    // Tu constructor existente
    public InscriptionController(InscriptionCommandService inscriptionCommandService, InscriptionQueryService inscriptionQueryService) {
        this.inscriptionCommandService = inscriptionCommandService;
        this.inscriptionQueryService = inscriptionQueryService;
    }

    @Operation(summary = "Create a inscription", description = "Creates a new inscription in the system.")
    @PostMapping
    public ResponseEntity<InscriptionResource> createInscription(@RequestBody CreateInscriptionResource resource) {
        var command = CreateInscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        try {
            var inscription = inscriptionCommandService.handle(command);
            return inscription.map(value ->
                    new ResponseEntity<>(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value), HttpStatus.CREATED)
            ).orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }

    @Operation(summary = "Get all inscriptions", description = "Retrieves a list of all registered inscriptions.")
    @GetMapping
    public ResponseEntity<List<InscriptionResource>> getAllInscriptions() {
        var getAllInscriptionsQuery = new GetAllInscriptionsQuery();
        var inscriptions = inscriptionQueryService.handle(getAllInscriptionsQuery);
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscriptionResources);
    }

    @Operation(summary = "Get inscription by ID", description = "Retrieves an inscription's details by its unique identifier.")
    @GetMapping("/{inscriptionId}")
    public ResponseEntity<InscriptionResource> getInscriptionById(@PathVariable Long inscriptionId) {
        var getInscriptionByIdQuery = new GetInscriptionByIdQuery(inscriptionId);
        var inscription = inscriptionQueryService.handle(getInscriptionByIdQuery);
        return inscription.map(value ->
                ResponseEntity.ok(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get inscription by activity ID", description = "Retrieves an inscription's details by activity ID.")
    @GetMapping("/byActivity/{activityId}")
    public ResponseEntity<List<InscriptionResource>> getInscriptionsByActivityId(@PathVariable Long activityId) {
        var query = new GetInscriptionsByActivityIdQuery(activityId);
        var inscriptions = inscriptionQueryService.handle(query);
        var inscriptionResources = inscriptions.stream()
                .map(InscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscriptionResources);
    }

    @Operation(summary = "Update an inscription", description = "Updates the details of an existing inscription.")
    @PutMapping("/{inscriptionId}")
    public ResponseEntity<InscriptionResource> updateInscription(@PathVariable Long inscriptionId, @RequestBody UpdateInscriptionResource resource) {
        var command = UpdateInscriptionCommandFromResourceAssembler.toCommandFromResource(inscriptionId, resource);
        try {
            var updatedInscription = inscriptionCommandService.handle(command);
            return updatedInscription.map(value ->
                    ResponseEntity.ok(InscriptionResourceFromEntityAssembler.toResourceFromEntity(value))
            ).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().header("X-Error-Message", e.getMessage()).build();
        }
    }

    @Operation(summary = "Delete an inscription", description = "Deletes an inscription from the system by its unique identifier.")
    @DeleteMapping("/{inscriptionId}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long inscriptionId) {
        try {
            var command = new DeleteInscriptionCommand(inscriptionId);
            inscriptionCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-Error-Message", e.getMessage())
                    .build();
        }
    }
}