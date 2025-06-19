package com.VolunTrack.demo.Participation.Interfaces.REST.Resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Resource for creating a new Certificate via REST requests.
 */
public record CreateCertificateResource(
        @NotNull @Positive Long participationId,
        @NotBlank @Size(min = 10, max = 1000) String description
) {
}