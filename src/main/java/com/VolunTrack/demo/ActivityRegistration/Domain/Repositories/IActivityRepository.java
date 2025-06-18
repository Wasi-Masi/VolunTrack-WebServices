package com.VolunTrack.demo.ActivityRegistration.Domain.Repositories;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {
    // Custom query methods can be added here if needed
    Optional<Activity> findByTitulo(String titulo);
}