package com.VolunTrack.demo.Participation.Domain.Repositories;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Participation entities.
 */
@Repository
public interface IParticipationRepository extends JpaRepository<Participation, Long> {
}