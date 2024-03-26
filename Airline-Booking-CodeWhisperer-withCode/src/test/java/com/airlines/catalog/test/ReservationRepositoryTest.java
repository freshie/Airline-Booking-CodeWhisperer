package com.airlines.catalog.test;

import com.airlines.catalog.FlightBookingApplication;

import com.airlines.catalog.model.Reservation;
import com.airlines.catalog.repository.ReservationRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/*
Create ReservationRepositoryTest class to test the reservationRepository class using
SpringBootTest with web environment and random port
*/

@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationRepositoryTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    ReservationRepository reservationRepository;

    /* Create a test method for successful save with valid data
   Attributes; flightId,travelClass, ticketPrice, currencyCode, paymentMode,contactNumber,
   contactEmail, reservationStatus, reservationDate, reservationTime, paymentStatus and passengerId.
   Assert BookingReference is not null
   */
    @Test
    public void testSaveReservation() {
        Reservation reservation = new Reservation();
        reservation.setFlightId(1);
        reservation.setTravelClass("Economy");
        reservation.setTicketPrice(1000);
        reservation.setCurrencyCode("USD");
        reservation.setPaymentMode("Credit Card");
        reservation.setContactNumber("1234567890");
        reservation.setContactEmail("XXXXXXXXXXXXX");
        reservation.setReservationStatus("Confirmed");
        reservation.setReservationDate("2022-01-01");
        reservation.setReservationTime("12:00:00");
        reservation.setPaymentStatus("Paid");
        reservation.setPassengerId(1);
        Reservation savedReservation = reservationRepository.save(reservation);
        Assert.assertNotNull(savedReservation.getBookingReference());
    }
}