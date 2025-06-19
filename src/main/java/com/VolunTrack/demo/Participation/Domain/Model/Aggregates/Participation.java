package com.VolunTrack.demo.Participation.Domain.Model.Aggregates;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Activity;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Participation aggregate in the Participation bounded context.
 * A participation record links a volunteer to a specific activity.
 */
@Entity
@Table(name = "participations")
@Getter
@Setter
@NoArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipationStatus status;

    /**
     * Constructor for creating a new Participation instance.
     *
     * @param volunteer The Volunteer participating.
     * @param activity The Activity in which the volunteer is participating.
     * @param status The initial status of the participation.
     */
    public Participation(Volunteer volunteer, Activity activity, ParticipationStatus status) {
        this.volunteer = volunteer;
        this.activity = activity;
        this.status = status;
    }

    public void updateStatus(ParticipationStatus newStatus) {
        this.status = newStatus;
    }
}