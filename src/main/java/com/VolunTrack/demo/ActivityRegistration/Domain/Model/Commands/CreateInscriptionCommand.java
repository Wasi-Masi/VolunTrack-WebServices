package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus;

import java.time.LocalDate;

public record CreateInscriptionCommand(
        Long voluntarioId,
        InscriptionStatus estado,
        LocalDate fecha,
        Long actividadId
) {
}