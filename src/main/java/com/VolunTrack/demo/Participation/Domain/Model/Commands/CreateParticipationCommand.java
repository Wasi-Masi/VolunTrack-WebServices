package com.VolunTrack.demo.Participation.Domain.Model.Commands;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.ParticipationStatus;

/**
 * Command to create a new Participation record.
 */
public record CreateParticipationCommand(
        Long volunteerId,
        Long activityId,
        ParticipationStatus initialStatus
) {
}