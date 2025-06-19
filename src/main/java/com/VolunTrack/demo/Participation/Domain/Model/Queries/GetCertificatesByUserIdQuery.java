package com.VolunTrack.demo.Participation.Domain.Model.Queries;

/**
 * Query to retrieve certificates associated with a specific volunteer's ID.
 * Certificates are indirectly linked to volunteers via participation records.
 */
public record GetCertificatesByUserIdQuery(
        Long userId // Note: This refers to the Volunteer's ID
) {
}