package com.VolunTrack.demo.Participation.Interfaces.REST.Controllers;

import com.VolunTrack.demo.Participation.Application.Internal.CommandServices.CertificateCommandService;
import com.VolunTrack.demo.Participation.Application.Internal.QueryServices.CertificateQueryService;
import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Certificate;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetCertificatesByUserIdQuery;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CertificateResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Resources.CreateCertificateResource;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CertificateResourceFromEntityAssembler;
import com.VolunTrack.demo.Participation.Interfaces.REST.Transform.CreateCertificateCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.VolunTrack.demo.response.ApiResponse;

/**
 * REST Controller for managing Certificates.
 * This controller handles HTTP requests related to certificate issuance and retrieval,
 * providing internationalized responses.
 */
@RestController
@RequestMapping(value = "/api/v1/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Certificates", description = "Certificate Management Endpoints")
public class CertificateController {

    private final CertificateCommandService certificateCommandService;
    private final CertificateQueryService certificateQueryService;
    private final MessageSource messageSource;

    public CertificateController(CertificateCommandService certificateCommandService,
                                 CertificateQueryService certificateQueryService,
                                 MessageSource messageSource) {
        this.certificateCommandService = certificateCommandService;
        this.certificateQueryService = certificateQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create a certificate", description = "Creates a new certificate in the system.")
    @PostMapping
    public ResponseEntity<ApiResponse<CertificateResource>> createCertificate(@RequestBody @Valid CreateCertificateResource resource) {
        Optional<Certificate> certificate = certificateCommandService.handle(
                CreateCertificateCommandFromResourceAssembler.toCommandFromResource(resource));

        return certificate.map(c ->
                new ResponseEntity<>(
                        ApiResponse.success(CertificateResourceFromEntityAssembler.toResourceFromEntity(c),
                                messageSource.getMessage("certificate.create.success", null, LocaleContextHolder.getLocale())),
                        HttpStatus.CREATED
                )
        ).orElseGet(() ->
                new ResponseEntity<>(
                        ApiResponse.<CertificateResource>error(
                                messageSource.getMessage("certificate.create.error.failed", null, LocaleContextHolder.getLocale()),
                                null),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @Operation(summary = "Get certificate by volunteer ID", description = "Retrieves a certification's details by volunteer ID.")
    @GetMapping("by-volunteer/{volunteerId}")
    public ResponseEntity<ApiResponse<List<CertificateResource>>> getCertificatesByVolunteerId(@PathVariable Long volunteerId) {
        List<Certificate> certificates = certificateQueryService.handle(new GetCertificatesByUserIdQuery(volunteerId));

        List<CertificateResource> resources = CertificateResourceFromEntityAssembler.toResourceListFromEntityList(certificates);

        if (resources.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(resources,
                    messageSource.getMessage("certificate.get_by_volunteer.no_data", new Object[]{volunteerId}, LocaleContextHolder.getLocale())));
        }

        return ResponseEntity.ok(ApiResponse.success(resources,
                messageSource.getMessage("certificate.get_by_volunteer.success", null, LocaleContextHolder.getLocale())));
    }
}