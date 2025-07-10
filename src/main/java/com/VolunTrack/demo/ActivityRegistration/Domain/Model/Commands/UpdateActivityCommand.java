package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Importante: Si esta clase no es un 'record', adapta esto a un constructor normal.
public record UpdateActivityCommand(
        Long actividadId,
        LocalDate fecha,
        LocalTime horaInicio,
        LocalTime horaFin,
        String titulo,
        String descripcion,
        String instrucciones,
        String proposito,
        int cupos,
        // ELIMINAR ESTA LÍNEA: int inscripcionesActuales, // Ya no es parte del comando
        String ubicacion,
        String estado,
        int organizacionId,
        List<ActivityImage> imagenes
) {
}