package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateActivityResource(
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