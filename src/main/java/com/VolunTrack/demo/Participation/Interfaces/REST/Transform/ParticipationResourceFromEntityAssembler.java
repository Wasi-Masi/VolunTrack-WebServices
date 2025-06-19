package com.VolunTrack.demo.Participation.Interfaces.REST.Transform;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.ParticipationResource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Assembler to transform a {@link Participation} entity into a {@link ParticipationResource}.
 */
public class ParticipationResourceFromEntityAssembler {

    public static ParticipationResource toResourceFromEntity(Participation entity) {
        return new ParticipationResource(
                entity.getId(),
                entity.getVolunteer().getId(),
                entity.getActivity().getActividad_id(),
                entity.getStatus()
        );
    }

    public static List<ParticipationResource> toResourceListFromEntityList(List<Participation> entities) {
        return entities.stream()
                .map(ParticipationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}