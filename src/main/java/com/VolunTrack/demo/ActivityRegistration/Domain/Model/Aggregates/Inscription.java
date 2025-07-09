package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus; // Importing InscriptionStatus enum for status handling
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents an Inscription aggregate in the Activity Registration bounded context.
 * The Inscription entity models the registration of a volunteer for an activity,
 * capturing the details of the volunteer's participation.
 * This is a root entity in the bounded context that is responsible for maintaining
 * the consistency of the inscription's state.
 */
@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscription_id; // Unique identifier for the inscription (auto-generated)

    @Column(name = "voluntario_id", nullable = false)
    private Long voluntarioId; // The ID of the volunteer making the inscription (foreign key)

    @Enumerated(EnumType.STRING) // Storing the enum as a string
    @Column(name = "estado", nullable = false, length = 50)
    private InscriptionStatus estado; // The status of the inscription (e.g., Pending, Confirmed, Canceled)

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // The date of the inscription (when the volunteer registered)

    @Column(name = "actividad_id", nullable = false)
    private Long actividadId; // The ID of the activity that the volunteer is registered for (foreign key)

    /**
     * Constructor to initialize an inscription with necessary parameters.
     * This constructor is used to create a new inscription record for a volunteer.
     * 
     * @param voluntarioId - The ID of the volunteer who is registering for the activity
     * @param estado - The current status of the inscription (e.g., Pending, Confirmed)
     * @param fecha - The date when the inscription was made
     * @param actividadId - The ID of the activity the volunteer is registering for
     */
    public Inscription(Long voluntarioId, InscriptionStatus estado, LocalDate fecha, Long actividadId) {
        this.voluntarioId = voluntarioId;
        this.estado = estado;
        this.fecha = fecha;
        this.actividadId = actividadId;
    }
}
