package com.VolunTrack.demo.ActivityRegistration.Domain.Repositories;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInscriptionRepository extends JpaRepository<Inscription, Long> {
    // Custom query methods
    List<Inscription> findByVoluntarioId(Long voluntarioId); // CHANGED
    List<Inscription> findByActividadId(Long actividadId);   // CHANGED
    Optional<Inscription> findByVoluntarioIdAndActividadId(Long voluntarioId, Long actividadId); // CHANGED
}