package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries;

/**
 * Represents a query to get an inscription by its unique ID.
 * This query is used in a CQRS (Command Query Responsibility Segregation) architecture
 * to fetch the details of a specific inscription based on its ID.
 */
public record GetInscriptionByIdQuery(Long inscriptionId) {
}
