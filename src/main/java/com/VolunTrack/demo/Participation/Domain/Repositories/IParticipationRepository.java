package com.VolunTrack.demo.Participation.Domain.Repositories;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByVolunteerAndActivity(Volunteer volunteer, Activity activity);
    List<Participation> findByVolunteer(Volunteer volunteer);
    List<Participation> findByActivity(Activity activity);
}