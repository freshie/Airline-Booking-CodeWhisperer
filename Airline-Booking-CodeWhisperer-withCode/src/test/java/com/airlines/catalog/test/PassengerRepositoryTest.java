package com.airlines.catalog.test;

import com.airlines.catalog.FlightBookingApplication;
import org.springframework.test.context.junit4.SpringRunner;
import com.airlines.catalog.model.Passenger;
import com.airlines.catalog.repository.PassengerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/* Create Junit test cases for PassengerRepository using
web environment with random port.
Create test case for save method by creating test data for firstName, lastName, adult and gender.
Assert that the passenger Id is not null
*/
@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerRepositoryTest {

    @Autowired
    PassengerRepository passengerRepository;

    @Test
    public void savePassengerTest() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Test");
        passenger.setLastName("Test");
        passenger.setAdult(true);
        passenger.setGender("M");
        passengerRepository.save(passenger);
        Assert.assertNotNull(passenger.getPassengerId());
    }
}

