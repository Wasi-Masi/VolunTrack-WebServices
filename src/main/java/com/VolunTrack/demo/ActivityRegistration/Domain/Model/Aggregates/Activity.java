package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects.ActivityImage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Activity aggregate in the Activity Registration bounded context.
 * An activity is a root entity that represents a volunteer opportunity.
 * This class is the aggregate root for the Activity entity, meaning it is responsible for maintaining 
 * the consistency of the activity's data and ensuring it follows business rules.
 */
@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actividad_id; // Unique identifier for the activity

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // The date of the activity

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio; // Start time of the activity

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin; // End time of the activity

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo; // The title of the activity

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion; // A description of the activity

    @Column(name = "instrucciones", length = 1000)
    private String instrucciones; // Any specific instructions for the activity

    @Column(name = "proposito", nullable = false, length = 500)
    private String proposito; // Purpose of the activity

    @Column(name = "cupos", nullable = false)
    private int cupos; // Number of total available slots for the activity

    @Column(name = "inscripciones_actuales", nullable = false)
    private int inscripcionesActuales; // Tracks the current number of enrolled volunteers

    @Column(name = "ubicacion", nullable = false, length = 250)
    private String ubicacion; // The location of the activity

    @Column(name = "estado", nullable = false, length = 50)
    private String estado; // The status of the activity (e.g., "Active", "Completed")

    @Column(name = "organizacion_id", nullable = false)
    private int organizacion_id; // The ID of the organization hosting the activity

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "activity_images", joinColumns = @JoinColumn(name = "actividad_id"))
    private List<ActivityImage> imagenes = new ArrayList<>(); // The images of an activity
    /**
     * Constructor to initialize an activity with all necessary attributes, including a default value for inscriptions.
     * @param fecha - Date of the activity
     * @param horaInicio - Start time of the activity
     * @param horaFin - End time of the activity
     * @param titulo - Title of the activity
     * @param descripcion - Description of the activity
     * @param instrucciones - Instructions for the activity
     * @param proposito - Purpose of the activity
     * @param cupos - Maximum available slots for the activity
     * @param ubicacion - Location of the activity
     * @param estado - Status of the activity
     * @param organizacion_id - ID of the organizing entity
     * @param imagenes - List of ActivityImage objects for the activity
     */
    public Activity(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, String titulo,
                    String descripcion, String instrucciones, String proposito, int cupos,
                    String ubicacion, String estado, int organizacion_id, List<ActivityImage> imagenes) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
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
        this.imagenes = (imagenes != null) ? new ArrayList<>(imagenes) : new ArrayList<>();
    }

    /**
     * Constructor to initialize an activity with a few mandatory attributes, and leaving instructions as optional.
     * @param fecha - Date of the activity
     * @param horaInicio - Start time of the activity
     * @param horaFin - End time of the activity
     * @param titulo - Title of the activity
     * @param descripcion - Description of the activity
     * @param proposito - Purpose of the activity
     * @param cupos - Maximum available slots for the activity
     * @param ubicacion - Location of the activity
     * @param estado - Status of the activity
     * @param organizacion_id - ID of the organizing entity
     */
    public Activity(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, String titulo,
                    String descripcion, String proposito, int cupos,
                    String ubicacion, String estado, int organizacion_id) {
        this(fecha, horaInicio, horaFin, titulo, descripcion, null, proposito, cupos, ubicacion, estado, organizacion_id, new ArrayList<>());
    }

    /**
     * Tries to increment the current number of inscriptions if there are available slots.
     * The method ensures that the number of inscriptions does not exceed the total number of available slots.
     * @return true if the number of inscriptions was incremented successfully, false if no slots are available.
     */
    public boolean tryIncrementInscriptionsActuales() {
        if (this.inscripcionesActuales < this.cupos) { // Check if there are available slots
            this.inscripcionesActuales++; // Increment the number of inscriptions
            return true;
        }
        return false; // Return false if no slots are available
    }

    /**
     * Tries to decrement the current number of inscriptions if it's greater than 0.
     * This method is used when a volunteer cancels or un-enrolls from the activity.
     * @return true if the number of inscriptions was decremented successfully, false if already 0.
     */
    public boolean tryDecrementInscriptionsActuales() {
        if (this.inscripcionesActuales > 0) { // Check if there are inscriptions to remove
            this.inscripcionesActuales--; // Decrement the number of inscriptions
            return true;
        }
        return false; // Return false if no inscriptions exist to remove
    }

    /**
     * Checks if the activity has available slots for new volunteers.
     * @return true if there are available slots, false if the activity is full.
     */
    public boolean hasAvailableSlots() {
        return this.inscripcionesActuales < this.cupos; // Returns true if current inscriptions are less than total slots
    }

    public void addImagen(String imageUrl) {
        this.imagenes.add(new ActivityImage(imageUrl));
    }

    public void addImagen(ActivityImage image) {
        this.imagenes.add(image);
    }

    public void removeImagen(ActivityImage image) {
        this.imagenes.remove(image);
    }
}
