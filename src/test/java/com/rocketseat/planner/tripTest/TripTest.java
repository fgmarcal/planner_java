package com.rocketseat.planner.tripTest;

import com.rocketseat.planner.exceptions.WrongDate;
import com.rocketseat.planner.trip.Trip;
import com.rocketseat.planner.trip.TripRepository;
import com.rocketseat.planner.trip.TripRequestPayload;
import com.rocketseat.planner.trip.TripService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripTest {

    @InjectMocks
    TripService tripService;

    @Mock
    TripRequestPayload payload;

    @Mock
    TripRepository tripRepository;

    @InjectMocks
    Trip trip;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trip = new Trip();
    }


    @Test
    public void shouldThrowErrorIfDatesAreWrong(){
        //Arrange
        //set end date as before start date
        when(payload.startsAt()).thenReturn(String.valueOf(LocalDateTime.parse("2024-07-25T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME)));
        when(payload.endsAt()).thenReturn(String.valueOf(LocalDateTime.parse("2024-07-24T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME)));
        //Act
        //Assert
            assertThrows(WrongDate.class, () -> tripService.createTrip(payload));
    }

    @SneakyThrows
    @Test
    public void shouldCreateATrip(){
        // Arrange
        when(payload.destination()).thenReturn("Test-TS");
        when(payload.ownerName()).thenReturn("Test");
        when(payload.ownerEmail()).thenReturn("test@test.com");
        when(payload.startsAt()).thenReturn("2024-07-25T21:51:54.734");
        when(payload.endsAt()).thenReturn("2024-07-25T21:51:54.734");

        Trip expectedTrip = new Trip(payload);
        when(tripRepository.save(any(Trip.class))).thenReturn(expectedTrip);

        // Act
        Trip result = tripService.createTrip(payload);

        // Assert
        assertEquals(expectedTrip.getDestination(), result.getDestination());
        assertEquals(expectedTrip.getOwnerName(), result.getOwnerName());
        assertEquals(expectedTrip.getOwnerEmail(), result.getOwnerEmail());
        assertEquals(expectedTrip.getStartsAt(), result.getStartsAt());
        assertEquals(expectedTrip.getEndsAt(), result.getEndsAt());

        verify(tripRepository, times(1)).save(any(Trip.class));

    }

    @Test
    public void shouldUpdateTripSuccessfully(){
        // Arrange
        UUID tripId = UUID.randomUUID();
        Trip existingTrip = new Trip();
        existingTrip.setDestination("Old Destination");
        existingTrip.setOwnerName("Test");
        existingTrip.setOwnerEmail("test@test.com");
        existingTrip.setStartsAt(LocalDateTime.parse("2024-07-25T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME));
        existingTrip.setEndsAt(LocalDateTime.parse("2024-07-26T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME));

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(existingTrip));
        when(payload.destination()).thenReturn("New Destination");
        when(payload.startsAt()).thenReturn("2024-07-25T21:51:54.734");
        when(payload.endsAt()).thenReturn("2024-07-26T21:51:54.734");

        // Act
        Optional<Trip> result = tripService.updateTrip(tripId, payload);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("New Destination", result.get().getDestination());
        verify(tripRepository, times(1)).save(existingTrip);
    }

    @Test
    public void shouldReturnEmptyWhenTripNotFound() {
        // Arrange
        UUID tripId = UUID.randomUUID();
        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        // Act
        Optional<Trip> result = tripService.updateTrip(tripId, payload);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnEmptyWhenWrongDateExceptionThrown() {
        // Arrange
        UUID tripId = UUID.randomUUID();
        Trip existingTrip = new Trip();
        existingTrip.setDestination("Old Destination");
        existingTrip.setOwnerName("Test");
        existingTrip.setOwnerEmail("test@test.com");
        existingTrip.setStartsAt(LocalDateTime.parse("2024-07-25T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME));
        existingTrip.setEndsAt(LocalDateTime.parse("2024-07-26T21:51:54.734", DateTimeFormatter.ISO_DATE_TIME));

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(existingTrip));
        when(payload.startsAt()).thenReturn("2024-07-26T21:51:54.734");
        when(payload.endsAt()).thenReturn("2024-07-25T21:51:54.734");

        // Act
        Optional<Trip> result = tripService.updateTrip(tripId, payload);

        // Assert
        assertFalse(result.isPresent());
        verify(tripRepository, never()).save(existingTrip);
    }


}
