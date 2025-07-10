package com.VolunTrack.demo.Participation.Domain.Services;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Repositories.ICertificateRepository;
import com.VolunTrack.demo.Participation.Domain.Repositories.IParticipationRepository;
import com.VolunTrack.demo.Shared.Domain.Repositories.IUnitOfWork;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
/**
 * Domain Service implementation for Certificate-related business operations.
 * This class handles logic for issuing and retrieving certificates.
 */
@Service
public class CertificateService implements ICertificateService {

    private final ICertificateRepository certificateRepository;
    private final IParticipationRepository participationRepository;
    private final IVolunteerRepository volunteerRepository;
    private final IUnitOfWork unitOfWork;
    private final MessageSource messageSource;

    public CertificateService(ICertificateRepository certificateRepository,
                              IParticipationRepository participationRepository,
                              IVolunteerRepository volunteerRepository,
                              IUnitOfWork unitOfWork,
                              MessageSource messageSource) {
        this.certificateRepository = certificateRepository;
        this.participationRepository = participationRepository;
        this.volunteerRepository = volunteerRepository;
        this.unitOfWork = unitOfWork;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public Optional<Certificate> issueCertificate(Long participationId, String description) {
        return participationRepository.findById(participationId).flatMap(participation -> {
            if (certificateRepository.findByParticipation(participation).isPresent()) {
                String msg = messageSource.getMessage("certificate.exists", new Object[]{participationId}, LocaleContextHolder.getLocale());
                System.out.println(msg);
                return Optional.empty();
            }

            Certificate certificate = new Certificate(description, participation);
            Certificate savedCertificate = certificateRepository.save(certificate);
            unitOfWork.complete();
            return Optional.of(savedCertificate);
        });
    }

    @Override
    public Optional<Certificate> getCertificateById(Long certificateId) {
        return certificateRepository.findById(certificateId);
    }

    @Override
    public Optional<Certificate> getCertificateByParticipationId(Long participationId) {
        return participationRepository.findById(participationId)
                .flatMap(certificateRepository::findByParticipation);
    }

    @Override
    public List<Certificate> getCertificatesByVolunteerId(Long volunteerId) {
        return volunteerRepository.findById(volunteerId)
                .map(volunteer -> {
                    List<Participation> participations = participationRepository.findByVolunteer(volunteer);
                    return participations.stream()
                            .flatMap(p -> certificateRepository.findByParticipation(p).stream())
                            .collect(Collectors.toList());
                })
                .orElse(List.of());
    }

    @Override
    @Transactional
    public boolean deleteCertificate(Long certificateId) {
        if (!certificateRepository.existsById(certificateId)) {
            return false;
        }
        certificateRepository.deleteById(certificateId);
        unitOfWork.complete();
        return true;
    }
}