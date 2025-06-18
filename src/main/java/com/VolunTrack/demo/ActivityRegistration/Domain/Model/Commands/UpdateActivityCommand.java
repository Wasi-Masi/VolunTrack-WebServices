package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateActivityCommand(
        Long actividadId,
        LocalDate fecha,
        LocalTime horainicio,
        LocalTime horaFin,
        String titulo,
        String descripcion,
        String instrucciones,
        String proposito,
        int cupos,
        String ubicacion,
        String estado,
        int organizacionId
) {
}