package com.VolunTrack.demo.Participation.Domain.Services;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import java.util.List;
import java.util.Optional;

/**
 * Domain Service interface for Certificate-related business operations.
 * This service handles logic for issuing and retrieving certificates.
 */
public interface ICertificateService {

    /**
     * Issues a new certificate for an existing participation.
     *
     * @param participationId The ID of the participation for which to issue the certificate.
     * @param description The content/description of the certificate.
     * @return An Optional containing the created Certificate if successful, or empty if validation fails (e.g., participation not found or certificate already exists for participation).
     */
    Optional<Certificate> issueCertificate(Long participationId, String description);

    /**
     * Retrieves a certificate by its ID.
     *
     * @param certificateId The ID of the certificate.
     * @return An Optional containing the Certificate if found, or empty otherwise.
     */
    Optional<Certificate> getCertificateById(Long certificateId);

    /**
     * Retrieves the certificate associated with a specific participation.
     *
     * @param participationId The ID of the participation.
     * @return An Optional containing the Certificate if found, or empty otherwise.
     */
    Optional<Certificate> getCertificateByParticipationId(Long participationId);

    /**
     * Retrieves all certificates issued for a specific volunteer.
     * This operation will involve querying participations first.
     *
     * @param volunteerId The ID of the volunteer.
     * @return A list of Certificates issued to the given volunteer.
     */
    List<Certificate> getCertificatesByVolunteerId(Long volunteerId);

    /**
     * Deletes a certificate by its ID.
     *
     * @param certificateId The ID of the certificate to delete.
     * @return True if the certificate was deleted successfully, false otherwise.
     */
    boolean deleteCertificate(Long certificateId);
}