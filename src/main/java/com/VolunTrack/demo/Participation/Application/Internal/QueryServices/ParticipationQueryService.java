package com.VolunTrack.demo.Participation.Application.Internal.QueryServices;

import com.VolunTrack.demo.Participation.Domain.Model.Aggregates.Participation;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByActivityIdQuery;
import com.VolunTrack.demo.Participation.Domain.Model.Queries.GetParticipationByUserIdQuery;
import com.VolunTrack.demo.Participation.Domain.Services.IParticipationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application Query Service for Participation.
 * Handles the execution of queries related to Participation, delegating to the domain service.
 */
@Service
public class ParticipationQueryService {

    private final IParticipationService participationService;

    public ParticipationQueryService(IParticipationService participationService) {
        this.participationService = participationService;
    }

    /**
     * Handles the GetParticipationByUserIdQuery.
     * Delegates the retrieval of participation records by volunteer ID to the domain service.
     *
     * @param query The query containing the volunteer ID.
     * @return A list of Participation records for the given volunteer.
     */
    public List<Participation> handle(GetParticipationByUserIdQuery query) {
        return participationService.getParticipationsByVolunteerId(query.userId());
    }

    /**
     * Handles the GetParticipationByActivityIdQuery.
     * Delegates the retrieval of participation records by activity ID to the domain service.
     *
     * @param query The query containing the activity ID.
     * @return A list of Participation records for the given activity.
     */
    public List<Participation> handle(GetParticipationByActivityIdQuery query) {
        return participationService.getParticipationsByActivityId(query.activityId());
    }

    /**
     * Retrieves a single participation by its ID. This method might not be explicitly
     * in the queries but is a common retrieval operation.
     *
     * @param participationId The ID of the participation.
     * @return An Optional containing the Participation if found, or empty.
     */
    public Optional<Participation> getParticipationById(Long participationId) {
        return participationService.getParticipationById(participationId);
    }
}