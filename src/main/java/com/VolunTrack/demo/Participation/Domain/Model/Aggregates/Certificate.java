package com.VolunTrack.demo.Participation.Domain.Model.Aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Certificate aggregate in the Participation bounded context.
 * A certificate is issued for a specific participation record.
 */
@Entity
@Table(name = "certificates")
@Getter
@Setter
@NoArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participation_id", unique = true, nullable = false)
    private Participation participation;

    /**
     * Constructor for creating a new Certificate instance.
     *
     * @param description The description or content of the certificate.
     * @param participation The Participation record for which this certificate is issued.
     */
    public Certificate(String description, Participation participation) {
        this.description = description;
        this.participation = participation;
    }
}