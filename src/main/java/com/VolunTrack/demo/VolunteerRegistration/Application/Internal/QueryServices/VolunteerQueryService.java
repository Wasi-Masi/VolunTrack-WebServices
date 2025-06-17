package com.VolunTrack.demo.VolunteerRegistration.Application.Internal.QueryServices;

import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Aggregates.Volunteer;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Model.Queries.GetAllVolunteersQuery;
import com.VolunTrack.demo.VolunteerRegistration.Domain.Repositories.IVolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Query service for retrieving Volunteer-related data.
 * This service handles incoming queries and delegates data retrieval to the volunteer repository.
 */
@Service
public class VolunteerQueryService {

    private final IVolunteerRepository volunteerRepository;

    /**
     * Constructs a new VolunteerQueryService.
     *
     * @param volunteerRepository The repository for Volunteer entities.
     */
    public VolunteerQueryService(IVolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Handles the query to retrieve all volunteers.
     *
     * @param query The query object (currently empty as no parameters are needed).
     * @return A list of all Volunteers.
     */
    public List<Volunteer> handle(GetAllVolunteersQuery query) {
        return volunteerRepository.findAll();
    }


}