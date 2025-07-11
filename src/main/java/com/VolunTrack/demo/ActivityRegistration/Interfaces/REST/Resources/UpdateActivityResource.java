package com.VolunTrack.demo.ActivityRegistration.Interfaces.REST.Resources;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record UpdateActivityResource(
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        String titulo,
        String descripcion,
        String instrucciones,
        String proposito,
        int cupos,
        String ubicacion,
        String estado,
        int organizacionId,
        List<String> imagenes
) {
}