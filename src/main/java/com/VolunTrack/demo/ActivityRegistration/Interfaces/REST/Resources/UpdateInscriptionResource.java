package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus;

import java.time.LocalDate;

public record UpdateInscriptionResource(
        Long voluntarioId,
        InscriptionStatus estado,
        LocalDate fecha,
        Long actividadId
) {
}