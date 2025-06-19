package com.VolunTrack.demo.Participation.Interfaces.REST.Resources;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.ParticipationStatus;

/**
 * Resource for representing a Participation record in REST responses.
 */
public record ParticipationResource(
        Long id,
        Long volunteerId,
        Long activityId,
        ParticipationStatus status
) {
}