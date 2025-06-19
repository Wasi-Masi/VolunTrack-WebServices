package com.VolunTrack.demo.Participation.Domain.Model.Queries;

/**
 * Query to retrieve participation records by a volunteer's ID.
 */
public record GetParticipationByUserIdQuery(
        Long userId // Note: This refers to the Volunteer's ID
) {
}