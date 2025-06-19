package com.VolunTrack.demo.Participation.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Participation.Application.Internal.CommandServices.CertificateCommandService;
import com.VolunTrack.demo.Participation.Application.Internal.QueryServices.CertificateQueryService;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetCertificatesByUserIdQuery;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CertificateResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateCertificateResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CertificateResourceFromEntityAssembler;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CreateCertificateCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing Certificates.
 */
@RestController
@RequestMapping(value = "/api/v1/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Certificates", description = "Certificate Management Endpoints")
public class CertificateController {

    private final CertificateCommandService certificateCommandService;
    private final CertificateQueryService certificateQueryService;

    public CertificateController(CertificateCommandService certificateCommandService, CertificateQueryService certificateQueryService) {
        this.certificateCommandService = certificateCommandService;
        this.certificateQueryService = certificateQueryService;
    }

    /**
     * Endpoint to issue a new certificate for an existing participation.
     * POST /api/v1/certificates
     */
    @PostMapping
    public ResponseEntity<CertificateResource> createCertificate(@RequestBody @Valid CreateCertificateResource resource) {
        Optional<Certificate> certificate = certificateCommandService.handle(
                CreateCertificateCommandFromResourceAssembler.toCommandFromResource(resource));

        return certificate.map(c -> new ResponseEntity<>(CertificateResourceFromEntityAssembler.toResourceFromEntity(c), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint to get certificates by volunteer ID.
     * GET /api/v1/certificates/by-volunteer/{volunteerId}
     */
    @GetMapping("by-volunteer/{volunteerId}")
    public ResponseEntity<List<CertificateResource>> getCertificatesByVolunteerId(@PathVariable Long volunteerId) {
        List<Certificate> certificates = certificateQueryService.handle(new GetCertificatesByUserIdQuery(volunteerId));
        if (certificates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CertificateResource> resources = CertificateResourceFromEntityAssembler.toResourceListFromEntityList(certificates);
        return ResponseEntity.ok(resources);
    }


}