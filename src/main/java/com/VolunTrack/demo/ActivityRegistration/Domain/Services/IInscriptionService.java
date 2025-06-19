package com.VolunTrack.demo.ActivityRegistration.Domain.Services;

import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Aggregates.Inscription;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.CreateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.DeleteInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands.UpdateInscriptionCommand;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetAllInscriptionsQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionByIdQuery;
import com.VolunTrack.demo.ActivityRegistration.Domain.Model.Queries.GetInscriptionsByActivityIdQuery; // NEW


import java.util.List;
import java.util.Optional;

public interface IInscriptionService {
    Optional<Inscription> handle(CreateInscriptionCommand command);
    Optional<Inscription> handle(UpdateInscriptionCommand command);
    void handle(DeleteInscriptionCommand command);
    List<Inscription> handle(GetAllInscriptionsQuery query);
    Optional<Inscription> handle(GetInscriptionByIdQuery query);
    List<Inscription> handle(GetInscriptionsByActivityIdQuery query); // NEW

}