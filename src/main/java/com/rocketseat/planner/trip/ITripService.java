package com.rocketseat.planner.trip;

import com.rocketseat.planner.exceptions.WrongDate;

import java.util.Optional;
import java.util.UUID;

public interface ITripService {

    private void validateDates(TripRequestPayload payload) throws WrongDate {}

    public Trip createTrip(TripRequestPayload payload);

    public Optional<Trip> getTripDetails(UUID tripId);

    public Optional<Trip> updateTrip(UUID id, TripRequestPayload payload);

    public Optional<Trip> confirmTrip(UUID id);


}
