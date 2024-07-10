package com.rocketseat.planner.participant;

import com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.participantRepository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = new Participant(email, trip);

        this.participantRepository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public Optional<Participant> confirmParticipant(UUID participantId, ParticipantRequestPayload payload){
        Optional<Participant> participant = this.participantRepository.findById(participantId);
        Participant rawParticipant = null;

        if(participant.isPresent()){
            rawParticipant = participant.get();
            rawParticipant.setConfirmed(true);
            rawParticipant.setName(payload.name());

            this.participantRepository.save(rawParticipant);
        }
        return Optional.ofNullable(rawParticipant);
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public void triggerConfirmationEmailToParticipant(String email){}

    public List<ParticipantData> getAllParticipantsFromEvent(UUID id){
        return this.participantRepository.findByTripId(id)
                .stream().map(participant ->
                        new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.isConfirmed()))
                        .toList();
    }
}
