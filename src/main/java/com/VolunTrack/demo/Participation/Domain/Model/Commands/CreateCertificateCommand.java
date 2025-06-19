package com.VolunTrack.demo.Participation.Domain.Model.Commands;

/**
 * Command to create a new Certificate for an existing Participation.
 */
public record CreateCertificateCommand(
        Long participationId,
        String description
) {
}