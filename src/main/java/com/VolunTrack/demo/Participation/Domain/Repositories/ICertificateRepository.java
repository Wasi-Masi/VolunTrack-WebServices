package com.VolunTrack.demo.Participation.Domain.Repositories;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Certificate entities.
 */
@Repository
public interface ICertificateRepository extends JpaRepository<Certificate, Long> {
}