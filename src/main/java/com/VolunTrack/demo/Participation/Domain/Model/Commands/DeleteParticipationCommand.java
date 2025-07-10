package com.VolunTrack.demo.Participation.Domain.Model.Commands;

/**
 * Command to delete an existing Participation record.
 *
 * @param participationId The unique identifier of the participation to be deleted.
 */
public record DeleteParticipationCommand(Long participationId) {
}