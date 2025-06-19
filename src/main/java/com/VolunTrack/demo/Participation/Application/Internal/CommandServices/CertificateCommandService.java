package com.VolunTrack.demo.Participation.Application.Internal.CommandServices;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Commands.CreateCertificateCommand;
import com.VolunTrack.demo.Participation.Domain.Services.ICertificateService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application Command Service for Certificate.
 * Handles the execution of commands related to Certificates, delegating to the domain service.
 */
@Service
public class CertificateCommandService {

    private final ICertificateService certificateService;

    public CertificateCommandService(ICertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Handles the CreateCertificateCommand.
     * Delegates the issuance of a certificate to the domain service.
     *
     * @param command The command containing the details for the new certificate.
     * @return An Optional containing the issued Certificate if successful, or empty otherwise.
     */
    public Optional<Certificate> handle(CreateCertificateCommand command) {
        return certificateService.issueCertificate(
                command.participationId(),
                command.description()
        );
    }
}