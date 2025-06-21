package com.VolunTrack.demo.Participation.Domain.Repositories;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * JPA repository for the Certificate entity.
 * Provides methods for performing CRUD operations and
 * custom queries on certificates.
*/
@Repository
public interface ICertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByParticipation(Participation participation);
}