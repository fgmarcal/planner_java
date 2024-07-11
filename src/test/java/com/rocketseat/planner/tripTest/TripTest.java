package com.rocketseat.planner.tripTest;

import com.rocketseat.planner.trip.ITripService;
import com.rocketseat.planner.trip.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TripTest {

    @Autowired
    ITripService tripService;

    @Test
    public void shouldThrowErrorIfDatesAreWrong(){
        //Arrange
        tripService = new TripService();
        //Act

        //Assert
    }
}
