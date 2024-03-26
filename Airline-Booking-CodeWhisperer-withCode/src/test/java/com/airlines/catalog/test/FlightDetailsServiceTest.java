package com.airlines.catalog.test;


import com.airlines.catalog.FlightBookingApplication;
import com.airlines.catalog.dto.FlightDetails;
import com.airlines.catalog.repository.FlightRepository;
import com.airlines.catalog.repository.AirportRepository;
import com.airlines.catalog.service.FlightDetailsService;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

/*
Create flightDetailsServiceTest class to test the FlightDetailsService using
web environment with random port. Test cases for testing findFlights method in this class which  returns
list of flightDetails.
*/

@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightDetailsServiceTest {

    @Autowired
    FlightDetailsService flightDetailsService;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @Value("${server.port}")
    private int port;

   /*
First test case method with no results using departure date as "2023-08-01", departure airport code as "CDG"
and arrival airport code as "LAX", flightresultsRepository and airportresultsRepository as parameters. Assert count as 0
*/
    @Test
    public void findFlightsTest() {
        List<FlightDetails> flightDetails = flightDetailsService.findFlights("2023-08-01", "CDG", "LAX", flightRepository, airportRepository);
        Assert.assertEquals(0, flightDetails.size());
    }
    /*
Second test case method with single results using departure date as "2023-08-02", departure airport code as "LHR",
 arrival airport code as "CDG", flightresultsRepository and airportresultsRepository as parameters. Assert count as 1
 */
    @Test
    public void findFlightsTest1() {
        List<FlightDetails> flightDetails = flightDetailsService.findFlights("2023-08-02", "LHR", "CDG", flightRepository, airportRepository);
        Assert.assertEquals(1, flightDetails.size());
    }

    /*
Third test case method with multiple results using departure date as "2023-08-01", departure airport code as "LHR",
arrival airport code as "CDG", flightresultsRepository and airportresultsRepository as parameters. Assert count as 2
*/
    @Test
    public void findFlightsTest2() {
        List<FlightDetails> flightDetails = flightDetailsService.findFlights("2023-08-01", "LHR", "CDG", flightRepository, airportRepository);
        Assert.assertEquals(2, flightDetails.size());
    }

}

