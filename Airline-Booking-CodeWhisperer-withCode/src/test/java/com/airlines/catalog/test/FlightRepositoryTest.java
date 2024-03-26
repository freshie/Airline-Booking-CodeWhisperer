package com.airlines.catalog.test;

import com.airlines.catalog.FlightBookingApplication;
import com.airlines.catalog.model.Flight;
import com.airlines.catalog.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/*
Create FlightRepositoryTest class to test the FlightRepository class using
web environment with random port.
Test cases for testing findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode method in this class
which returns an iterable of Flights:
First test case method with no results using departure date as "2023-08-01", departure airport code as "CDG"
and arrival airport code as "LAX" which should return a string "[]".
Second test case method with single results using departure date as "2023-08-02", departure airport code as "LHR"
and arrival airport code as "CDG" which should return a airport iterable. Assert count as 1.
Third test case method with multiple results using departure date as "2023-08-01", departure airport code as "LHR"
and arrival airport code as "CDG" which should return a airport iterable. Assert count as 2.
*/
@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Value("${local.server.port}")
    private int port;

    @Test
    public void findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode_NoResults() {
        Iterable<Flight> flights = flightRepository.findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode("2023-08-01", "CDG", "LAX");
        Assert.assertEquals("[]", flights.toString());
    }

    @Test
    public void findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode_SingleResult() {
        Iterable<Flight> flights = flightRepository.findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode("2023-08-02", "LHR", "CDG");
        Assert.assertEquals(1, ((java.util.List<Flight>) flights).size());
    }

    @Test
    public void findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode_MultipleResults() {
        Iterable<Flight> flights = flightRepository.findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode("2023-08-01", "LHR", "CDG");
        Assert.assertEquals(2, ((java.util.List<Flight>) flights).size());
    }

}








