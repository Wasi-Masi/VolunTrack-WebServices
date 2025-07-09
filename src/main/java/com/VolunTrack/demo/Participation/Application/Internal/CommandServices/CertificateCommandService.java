package com.VolunTrack.demo.Participation.Application.Internal.CommandServices;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Commands.CreateCertificateCommand;
import com.VolunTrack.demo.Participation.Domain.Services.ICertificateService;
import com.VolunTrack.demo.Notifications.Domain.Services.INotificationCommandService;
import com.VolunTrack.demo.Notifications.Domain.Model.Commands.CreateNotificationCommand;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.NotificationType;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
import com.VolunTrack.demo.ActivityRegistration.Domain.Repositories.IInscriptionRepository;
import com.VolunTrack.demo.Participation.Domain.Repositories.IParticipationRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.context.MessageSource;
/**
 * Application Command Service for Certificate.
 * Handles the execution of commands related to Certificates, delegating to the domain service.
 */
@Service
public class CertificateCommandService {

    private final ICertificateService certificateService;
    private final INotificationCommandService notificationCommandService;
    private final IParticipationRepository participationRepository;
    private final IInscriptionRepository inscriptionRepository;
    private final MessageSource messageSource;

    public CertificateCommandService(ICertificateService certificateService, INotificationCommandService notificationCommandService, IParticipationRepository participationRepository, IInscriptionRepository inscriptionRepository, MessageSource messageSource) {
        this.certificateService = certificateService;
        this.notificationCommandService = notificationCommandService;
        this.participationRepository = participationRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.messageSource = messageSource;
    }

    /**
     * Handles the CreateCertificateCommand.
     * Delegates the issuance of a certificate to the domain service.
     *
     * @param command The command containing the details for the new certificate.
     * @return An Optional containing the issued Certificate if successful, or empty otherwise.
     */
    public Optional<Certificate> handle(CreateCertificateCommand command) {
        Optional<Certificate> issuedCertificate = certificateService.issueCertificate(
                command.participationId(),
                command.description()
        );

        issuedCertificate.ifPresent(certificate -> {

            try {

                participationRepository.findById(command.participationId()).ifPresent(participation -> {
                    inscriptionRepository.findById(participation.getId()).ifPresent(inscription -> {
                        CreateNotificationCommand notificationCommand = new CreateNotificationCommand(
                                NotificationType.CERTIFICATE_READY,
                                inscription.getVoluntarioId(),
                                RecipientType.VOLUNTEER
                        );
                        notificationCommandService.handle(notificationCommand);
                    });
                });
            } catch (Exception e) {
                String errorLog = messageSource.getMessage(
                        "certificate.notificationErrorLog",
                        new Object[]{command.participationId(), e.getMessage()},
                        LocaleContextHolder.getLocale()
                );
                System.err.println(errorLog);
            }
        });

        return issuedCertificate;
    }
}