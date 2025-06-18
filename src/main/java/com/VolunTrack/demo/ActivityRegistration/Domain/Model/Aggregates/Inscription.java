package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Enums.InscriptionStatus;
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

@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inscription_id;

    @Column(name = "voluntario_id", nullable = false)
    private Long voluntarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 50)
    private InscriptionStatus estado;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "actividad_id", nullable = false)
    private Long actividadId;

    public Inscription(Long voluntarioId, InscriptionStatus estado, LocalDate fecha, Long actividadId) {
        this.voluntarioId = voluntarioId;
        this.estado = estado;
        this.fecha = fecha;
        this.actividadId = actividadId;
    }


}