package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates;

import  jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an Activity aggregate in the Activity Registration bounded context.
 * An activity is a root entity that represents a volunteer opportunity.
 */
@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actividad_id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horainicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "instrucciones", length = 1000)
    private String instrucciones;

    @Column(name = "proposito", nullable = false, length = 500)
    private String proposito;

    @Column(name = "cupos", nullable = false)
    private int cupos; // Number of total available slots

    // NEW FIELD: To track current number of enrolled volunteers
    @Column(name = "inscripciones_actuales", nullable = false)
    private int inscripcionesActuales;

    @Column(name = "ubicacion", nullable = false, length = 250)
    private String ubicacion;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "organizacion_id", nullable = false)
    private int organizacion_id;

    public Activity(LocalDate fecha, LocalTime horainicio, LocalTime horaFin, String titulo,
                    String descripcion, String instrucciones, String proposito, int cupos,
                    String ubicacion, String estado, int organizacion_id) {
        this.fecha = fecha;
        this.horainicio = horainicio;
        this.horaFin = horaFin;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.proposito = proposito;
        this.cupos = cupos;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.organizacion_id = organizacion_id;
        this.inscripcionesActuales = 0; // Initialize to 0 when creating a new activity
    }

    public Activity(LocalDate fecha, LocalTime horainicio, LocalTime horaFin, String titulo,
                    String descripcion, String proposito, int cupos,
                    String ubicacion, String estado, int organizacion_id) {
        this(fecha, horainicio, horaFin, titulo, descripcion, null, proposito, cupos, ubicacion, estado, organizacion_id);
    }

    /**
     * Increments the current number of inscriptions if there are available slots.
     * @return true if incremented successfully, false if no slots are available.
     */
    public boolean tryIncrementInscriptionsActuales() {
        if (this.inscripcionesActuales < this.cupos) {
            this.inscripcionesActuales++;
            return true;
        }
        return false;
    }

    /**
     * Decrements the current number of inscriptions if it's greater than 0.
     * Useful for un-enrollment/cancellation.
     * @return true if decremented successfully, false if already 0.
     */
    public boolean tryDecrementInscriptionsActuales() {
        if (this.inscripcionesActuales > 0) {
            this.inscripcionesActuales--;
            return true;
        }
        return false;
    }

    public boolean hasAvailableSlots() {
        return this.inscripcionesActuales < this.cupos;
    }
}