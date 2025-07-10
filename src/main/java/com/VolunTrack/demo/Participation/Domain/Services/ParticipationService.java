package com.VolunTrack.demo.Participation.Domain.Services;

import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IActivityRepository;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.ParticipationStatus;
import com.VolunTrack.demo.Participation.Domain.Repositories.IParticipationRepository;
import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.context.MessageSource;

@Service
public class ParticipationService implements IParticipationService {

    private final IParticipationRepository participationRepository;
    private final IVolunteerRepository volunteerRepository;
    private final IActivityRepository activityRepository;
    private final IUnitOfWork unitOfWork;
    private final MessageSource messageSource;

    public ParticipationService(IParticipationRepository participationRepository,
                                IVolunteerRepository volunteerRepository,
                                IActivityRepository activityRepository,
                                IUnitOfWork unitOfWork,
                                MessageSource messageSource) {
        this.participationRepository = participationRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityRepository = activityRepository;
        this.unitOfWork = unitOfWork;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public Optional<Participation> createParticipation(Long volunteerId, Long activityId, ParticipationStatus initialStatus) {
        return volunteerRepository.findById(volunteerId).flatMap(volunteer -> {
            return activityRepository.findById(activityId).flatMap(activity -> {
                if (participationRepository.findByVolunteerAndActivity(volunteer, activity).isPresent()) {
                    String msg = messageSource.getMessage("participation.duplicate", new Object[]{volunteerId, activityId}, LocaleContextHolder.getLocale());
                    System.out.println(msg);
                    return Optional.empty();
                }

                Participation participation = new Participation(volunteer, activity, initialStatus);
                Participation savedParticipation = participationRepository.save(participation);
                unitOfWork.complete();
                return Optional.of(savedParticipation);
            });
        });
    }

    @Override
    public Optional<Participation> getParticipationById(Long participationId) {
        return participationRepository.findById(participationId);
    }

    @Override
    public List<Participation> getParticipationsByVolunteerId(Long volunteerId) {
        return volunteerRepository.findById(volunteerId)
                .map(participationRepository::findByVolunteer)
                .orElse(List.of());
    }

    @Override
    public List<Participation> getParticipationsByActivityId(Long activityId) {
        return activityRepository.findById(activityId)
                .map(participationRepository::findByActivity)
                .orElse(List.of());
    }

    @Override
    @Transactional
    public Optional<Participation> updateParticipationStatus(Long participationId, ParticipationStatus newStatus) {
        return participationRepository.findById(participationId).flatMap(participation -> {
            participation.updateStatus(newStatus);
            Participation updatedParticipation = participationRepository.save(participation);
            unitOfWork.complete();
            return Optional.of(updatedParticipation);
        });
    }

    @Override
    @Transactional
    public boolean deleteParticipation(Long participationId) {
        if (!participationRepository.existsById(participationId)) {
            return false;
        }
        participationRepository.deleteById(participationId);
        unitOfWork.complete();
        return true;
    }
}