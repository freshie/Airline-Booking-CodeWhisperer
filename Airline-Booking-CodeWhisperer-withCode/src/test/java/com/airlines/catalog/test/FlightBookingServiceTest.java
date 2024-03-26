package com.airlines.catalog.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.airlines.catalog.FlightBookingApplication;
import com.airlines.catalog.model.Reservation;
import com.airlines.catalog.model.Passenger;
import com.airlines.catalog.repository.ReservationRepository;
import com.airlines.catalog.repository.PassengerRepository;
import com.airlines.catalog.repository.FlightRepository;
import com.airlines.catalog.service.FlightBooking;
import com.airlines.catalog.exception.FlightNotFoundException;
import com.airlines.catalog.exception.RequestedSeatsNotAvailable;
import software.amazon.awssdk.regions.Region;

/*
Create FlightBookingTest class to test the FlightBooking service using
web environment with random port.
Autowire sns.arn, aws.region from properties file
*/
@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightBookingServiceTest {

    @Autowired
    FlightBooking flightBooking;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Value("${sns.arn}")
    String snsArn;

    @Value("${aws.region}")
    String awsRegion;

    /*
    Create reserve flight success test method.
    Create Passenger object by setting attributes firstName, lastName, Adult=true and gender=male
    create Reservation object class by setting attributes flightId=1,travelClass, ticketPrice,
    currencyCode, paymentMode,contactNumber, contactEmail, reservationStatus,passengerId, reservationDate,
    reservationTime, paymentStatus
    Create AWS region object
    */
    @Test
    public void reserveFlightSuccessTest() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("XXXX");
        passenger.setLastName("XXX");
        passenger.setAdult(true);
        passenger.setGender("male");
        Reservation reservation = new Reservation();
        reservation.setFlightId(1);
        reservation.setTravelClass("economy");
        reservation.setTicketPrice(100);
        reservation.setCurrencyCode("USD");
        reservation.setPaymentMode("credit card");
        reservation.setContactNumber("1234567890");
        reservation.setContactEmail("abc@gmail.com");
        reservation.setReservationStatus("pending");
        reservation.setPaymentStatus("Paid");
        reservation.setReservationDate("2023-10-25");
        reservation.setReservationTime("12:00:00");
        reservation.setPassengerId(passenger.getPassengerId());
        Region region = Region.of(awsRegion);

        Assert.assertTrue(flightBooking.reserveFlight(passenger, reservation, passengerRepository,
                reservationRepository, flightRepository, 1, snsArn, region));


    }

    /*
    Create  reserve flight invalid flightId test method.
    Create passenger object by setting attributes firstName, lastName, Adult=true and gender
    Create reservation object class by setting attributes flightId=10000,travelClass, ticketPrice,
    currencyCode, paymentMode,contactNumber, contactEmail, reservationStatus, passengerId, reservationDate,
    reservationTime, paymentStatus
    Create AWS region object
    asset if the exception contains 'No flights found"
    */
    @Test
    public void reserveFlightInvalidFlightIdTest() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("XXXX");
        passenger.setLastName("XXX");
        passenger.setAdult(true);
        passenger.setGender("male");
        Reservation reservation = new Reservation();
        reservation.setFlightId(10000);
        reservation.setTravelClass("economy");
        reservation.setTicketPrice(100);
        reservation.setCurrencyCode("USD");
        reservation.setPaymentMode("credit card");
        reservation.setContactNumber("1234567890");
        reservation.setContactEmail("abc@gmail.com");
        reservation.setReservationStatus("pending");
        reservation.setPaymentStatus("Paid");
        reservation.setReservationDate("2023-10-25");
        reservation.setReservationTime("12:00:00");
        reservation.setPassengerId(passenger.getPassengerId());
        Region region = Region.of(awsRegion);
        try {
            Assert.assertThrows(FlightNotFoundException.class, () -> flightBooking.reserveFlight(passenger, reservation, passengerRepository,
                    reservationRepository, flightRepository, 10000, snsArn, region));
        } catch (FlightNotFoundException e) {
            Assert.assertEquals("No flights found", e.getMessage());
        }

    }

    /*
Create  reserve flight insufficient seats method.
Create passenger object by setting attributes firstName, lastName, Adult=true and gender
Create reservation object class by setting attributes flightId=5,travelClass, ticketPrice,
currencyCode, paymentMode,contactNumber, contactEmail, reservationStatus, passengerId,
reservationDate, reservationTime, paymentStatus
Create AWS region object.
asset if the exception contains 'Seats not available".
*/
    @Test
    public void reserveFlightInsufficientSeatsTest() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Test");
        passenger.setLastName("Test");
        passenger.setAdult(true);
        passenger.setGender("male");
        Reservation reservation = new Reservation();
        reservation.setFlightId(5);
        reservation.setTravelClass("economy");
        reservation.setTicketPrice(100);
        reservation.setCurrencyCode("USD");
        reservation.setPaymentMode("credit card");
        reservation.setContactNumber("1234567890");
        reservation.setContactEmail("test@gmail.com");
        reservation.setReservationStatus("pending");
        reservation.setPaymentStatus("Paid");
        reservation.setReservationDate("2023-10-25");
        reservation.setReservationTime("12:00:00");
        reservation.setPassengerId(passenger.getPassengerId());
        Region region = Region.of(awsRegion);
        try {
            Assert.assertThrows(RequestedSeatsNotAvailable.class, () -> flightBooking.reserveFlight(passenger, reservation, passengerRepository,
                    reservationRepository, flightRepository, 5, snsArn, region));
        } catch (RequestedSeatsNotAvailable e) {
            Assert.assertEquals("Seats not available", e.getMessage());
        }
    }
}
