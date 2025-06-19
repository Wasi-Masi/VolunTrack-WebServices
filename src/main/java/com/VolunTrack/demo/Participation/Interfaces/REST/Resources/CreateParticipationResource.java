package com.VolunTrack.demo.Participation.Interfaces.REST.Resources;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.ParticipationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Resource for creating a new Participation record via REST requests.
 */
public record CreateParticipationResource(
        @NotNull @Positive Long volunteerId,
        @NotNull @Positive Long activityId,
        @NotNull ParticipationStatus initialStatus
) {
}