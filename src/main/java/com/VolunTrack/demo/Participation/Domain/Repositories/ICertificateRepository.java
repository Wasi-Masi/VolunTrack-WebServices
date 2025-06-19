package com.VolunTrack.demo.Participation.Domain.Repositories;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation; // <-- Agrega esta importación
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- Asegúrate de que esta importación exista

@Repository
public interface ICertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByParticipation(Participation participation);
}