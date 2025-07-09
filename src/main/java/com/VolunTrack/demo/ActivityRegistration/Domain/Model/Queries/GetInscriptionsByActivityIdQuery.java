package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries;

/**
 * Represents a query to get all inscriptions for a specific activity.
 * This query is used in a CQRS (Command Query Responsibility Segregation) architecture
 * to fetch the list of inscriptions for a particular activity.
 */
public record GetInscriptionsByActivityIdQuery(Long activityId) {
}
