package com.VolunTrack.demo.Participation.Domain.Model.Queries;

/**
 * Query to retrieve participation records by an activity's ID.
 */
public record GetParticipationByActivityIdQuery(
        Long activityId
) {
}