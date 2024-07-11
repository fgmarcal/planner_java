package com.rocketseat.planner.trip;

import com.rocketseat.planner.exceptions.WrongDate;
import com.rocketseat.planner.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService implements ITripService{

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

     private void validateDates(TripRequestPayload payload) throws WrongDate{
        var startsAt = LocalDateTime.parse(payload.startsAt(), DateTimeFormatter.ISO_DATE_TIME);
        var endsAt = LocalDateTime.parse(payload.endsAt(), DateTimeFormatter.ISO_DATE_TIME);
        if(endsAt.isBefore(startsAt)){
            throw new WrongDate("Data final é menor do que data inicial");
        }
    }

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
        }

        try{
            validateDates(payload);
            rawTrip.setDestination(payload.destination());
            this.tripRepository.save(rawTrip);

        }catch (WrongDate e){
            System.err.println(e.getMessage());;
            return Optional.ofNullable(null);
        }catch (Exception e){
            System.err.println(e.getMessage());;
            return Optional.ofNullable(null);
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
