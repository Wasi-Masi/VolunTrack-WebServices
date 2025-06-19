package com.VolunTrack.demo.Participation.Interfaces.REST.Resources;

/**
 * Resource for representing a Certificate record in REST responses.
 */
public record CertificateResource(
        Long id,
        String description,
        Long participationId
) {
}