package com.airlines.catalog.test;

import com.airlines.catalog.FlightBookingApplication;
import com.airlines.catalog.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
Create AirportRepositoryTest class to test the AirportRepository class using
web environment with random port.
First negative test case: method with Invalid airport code - "LHG" which should assert a null object
Second positive test case: method with valid airport code - "LHR" which should assert a not null object
*/
@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirportRepositoryTest {

    @Autowired
    AirportRepository airportRepository;

    @Value("${server.port}")
    int port;

    @Test
    public void testAirportRepositoryWithInvalidAirportCode() {
        Assert.assertNull(airportRepository.findByAirportCode("LHG"));
    }

    @Test
    public void testAirportRepositoryWithValidAirportCode() {
        Assert.assertNotNull(airportRepository.findByAirportCode("LHR"));
    }
}