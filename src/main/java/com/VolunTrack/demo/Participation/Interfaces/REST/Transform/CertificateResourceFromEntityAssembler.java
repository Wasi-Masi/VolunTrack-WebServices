package com.VolunTrack.demo.Participation.Interfaces.REST.Transform;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CertificateResource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Assembler to transform a {@link Certificate} entity into a {@link CertificateResource}.
 */
public class CertificateResourceFromEntityAssembler {

    public static CertificateResource toResourceFromEntity(Certificate entity) {
        return new CertificateResource(
                entity.getId(),
                entity.getDescription(),
                entity.getParticipation().getId()
        );
    }

    public static List<CertificateResource> toResourceListFromEntityList(List<Certificate> entities) {
        return entities.stream()
                .map(CertificateResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}