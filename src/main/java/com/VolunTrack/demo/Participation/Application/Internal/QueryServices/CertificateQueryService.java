package com.VolunTrack.demo.Participation.Application.Internal.QueryServices;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetCertificatesByUserIdQuery;
import com.VolunTrack.demo.Participation.Domain.Services.ICertificateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application Query Service for Certificate.
 * Handles the execution of queries related to Certificates, delegating to the domain service.
 */
@Service
public class CertificateQueryService {

    private final ICertificateService certificateService;

    public CertificateQueryService(ICertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Handles the GetCertificatesByUserIdQuery.
     * Delegates the retrieval of certificates by volunteer ID to the domain service.
     *
     * @param query The query containing the volunteer ID.
     * @return A list of Certificates issued to the given volunteer.
     */
    public List<Certificate> handle(GetCertificatesByUserIdQuery query) {
        return certificateService.getCertificatesByVolunteerId(query.userId());
    }

    /**
     * Retrieves a single certificate by its ID.
     *
     * @param certificateId The ID of the certificate.
     * @return An Optional containing the Certificate if found, or empty.
     */
    public Optional<Certificate> getCertificateById(Long certificateId) {
        return certificateService.getCertificateById(certificateId);
    }

    /**
     * Retrieves a certificate by its associated participation ID.
     *
     * @param participationId The ID of the participation.
     * @return An Optional containing the Certificate if found, or empty.
     */
    public Optional<Certificate> getCertificateByParticipationId(Long participationId) {
        return certificateService.getCertificateByParticipationId(participationId);
    }
}