package com.rocketseat.planner.trip;

import com.rocketseat.planner.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    public Trip createTrip(TripRequestPayload payload){
        Trip newTrip = new Trip(payload);
        return this.tripRepository.save(newTrip);
    }

    public Optional<Trip> getTripDetails(UUID tripId){
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        return trip;
    }

    public Optional<Trip> updateTrip(UUID id, TripRequestPayload payload){
        Optional<Trip> trip = this.tripRepository.findById(id);
        Trip rawTrip = null;
        if(trip.isPresent()){
            rawTrip = trip.get();

            rawTrip.setStartsAt(LocalDateTime.parse(payload.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setEndsAt(LocalDateTime.parse(payload.endsAt(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setDestination(payload.destination());

            this.tripRepository.save(rawTrip);
        }
        return Optional.ofNullable(rawTrip);
    }

    public Optional<Trip> confirmTrip(UUID id){
        Optional<Trip> trip = this.tripRepository.findById(id);
        Trip rawTrip = null;
        if(trip.isPresent()){
            rawTrip = trip.get();
            rawTrip.setConfirmed(true);

            this.tripRepository.save(rawTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);
        }
        return Optional.ofNullable(rawTrip);
    }
}
