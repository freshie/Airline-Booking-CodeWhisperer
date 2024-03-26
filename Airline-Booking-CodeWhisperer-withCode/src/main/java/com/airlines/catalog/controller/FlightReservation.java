package com.airlines.catalog.controller;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import com.airlines.catalog.dto.FlightDetails;
import com.airlines.catalog.exception.AuthenticationException;
import com.airlines.catalog.repository.AirportRepository;
import com.airlines.catalog.repository.FlightRepository;
import com.airlines.catalog.service.FlightDetailsService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airlines.catalog.repository.PassengerRepository;
import com.airlines.catalog.repository.ReservationRepository;
import com.airlines.catalog.service.FlightBooking;
import com.airlines.catalog.dto.ReservationDetails;
import com.airlines.catalog.exception.FlightNotFoundException;
import com.airlines.catalog.exception.RequestedSeatsNotAvailable;
import com.airlines.catalog.model.Passenger;
import com.airlines.catalog.model.Reservation;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.regions.Region;

import javax.validation.Valid;


@RestController
public class FlightReservation {
    @Autowired
    FlightRepository flightresultsRepository;
    @Autowired
    AirportRepository airportresultsRepository;
    @Autowired
    FlightDetailsService FlightDetailsService;

    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    FlightBooking flightBooking;

    @Value("${cognito.userpool.id}")
    private String cognitoUserPoolId;
    @Value("${aws.region}")
    private String awsRegion;
    @Value("${sns.arn}")
    private String snsTopicArn;

    /* Create a private method verifyToken to verify JWT token with input parameters as
    Cognito user pool id, AWS region and token string. Function returns a Boolean.
    Construct the Cognito well known url and then verify the token using RSA Algorithm.
    catch all Exception throw new authenticationException.*/
    private Boolean verifyToken(String cognitoUserPoolId, String awsRegion, String token) throws AuthenticationException {
        try {
            System.out.println("token=" + token);

            String cognitoWellKnownUrl = "https://cognito-idp." + awsRegion + ".amazonaws.com/" + cognitoUserPoolId + "/.well-known/jwks.json";
            Algorithm algorithm = Algorithm.RSA256(new AwsCognitoRSAKeyProvider(cognitoWellKnownUrl));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    /* Create a rest controller getFlightDetails to get flight details with HTTP GET Method ,
   /flight path and request parameters as departure date, departure airport code and arrival airport code
   JWT Token in the Authorization Header.
   Rest controller returns a ResponseEntity of string.
   */
    @GetMapping("/flight")
    public ResponseEntity<String> getFlightDetails(@RequestParam("departureDate") String departureDate,
                                                   @RequestParam("departureAirportCode") String departureAirportCode,
                                                   @RequestParam("arrivalAirportCode") String arrivalAirportCode,
                                                   @RequestHeader("Authorization") String authorization) throws AuthenticationException {
        /* Call verifyToken method, catch authentication exception and return the responseEntity
    if token is valid and call the findFlights method in FlightDetailsService class
    with input parameters  departure date, departure airport code,
    arrival airport code, flightResultsRepository and airportResultsRepository.
    If flights are found return the list of flights otherwise return "No flights found".
    If authentication failed the return "Authentication failed" and HTTP status of forbidden
    */
        try {
            if (verifyToken(cognitoUserPoolId, awsRegion, authorization)) {
                List<FlightDetails> flights = FlightDetailsService.findFlights(departureDate, departureAirportCode, arrivalAirportCode, flightresultsRepository, airportresultsRepository);
                if (flights.size() > 0) {
                    return new ResponseEntity<>(flights.toString(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No flights found", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Authentication failed", HttpStatus.FORBIDDEN);
            }
        } catch (AuthenticationException e) {
            return e.getResponseEntity();
        }

    }

    /* Create a rest controller bookFlight to get flight details with HTTP POST Method ,
/reserve path and request body reservationDetails. Rest controller returns a ResponseEntity of string.
Authorization token is passed in the header of the request. Validate the request body
*/

    @PostMapping("/reserve")
    public ResponseEntity<String> bookFlight(@RequestBody @Valid ReservationDetails reservationDetails,
                                             @RequestHeader("Authorization") String authorization)
            throws AuthenticationException, FlightNotFoundException, RequestedSeatsNotAvailable {
         ResponseEntity<String> responseEntity = null;
        /*validate the token by calling verifyToken method in a try catch block.
Catch authenticationExceptionHandler exception return the response entity object
from the exception object*/

        try {
            if (verifyToken(cognitoUserPoolId, awsRegion, authorization)) {
                /* create passenger object assign first name, last name and gender of passenger object from reservationDetails object */
                Passenger passenger = new Passenger();
                passenger.setFirstName(reservationDetails.getFirstName());
                passenger.setLastName(reservationDetails.getLastName());
                passenger.setGender(reservationDetails.getGender());
                //Check the age from reservationDetails object and populate adult field
                if (reservationDetails.getAge() >= 18) {
                    passenger.setAdult(true);
                }
                /* create reservation object Assign flightId, travelClass, ticketPrice, currencyCode,contactEmail,
                 contactNumber, reservationStatus, paymentStatus, paymentMode */
                Reservation reservation = new Reservation();
                reservation.setFlightId(reservationDetails.getFlightId());
                reservation.setTravelClass(reservationDetails.getTravelClass());
                reservation.setTicketPrice(reservationDetails.getTicketPrice());
                reservation.setCurrencyCode(reservationDetails.getCurrencyCode());
                reservation.setContactEmail(reservationDetails.getContactEmail());
                reservation.setContactNumber(reservationDetails.getContactNumber());
                reservation.setReservationStatus("Pending");
                reservation.setPaymentStatus("Pending");
                reservation.setPaymentMode("Cash");
                /* set the date reservation date in yyyy-MM-dd format and
                reservation time in HH:mm:ss format for current date and time*/
                reservation.setReservationDate(java.time.LocalDate.now().toString());
                reservation.setReservationTime(java.time.LocalTime.now().toString());

                Boolean result;
                try {
                    int noOfPassengers = 1;
                    Region region = Region.of(awsRegion);
                    result = flightBooking.reserveFlight(passenger, reservation, passengerRepository,
                            reservationRepository, flightresultsRepository, noOfPassengers, snsTopicArn, region);
                }
                catch (FlightNotFoundException e) {
                    return e.getResponseEntity();
                }
                catch (RequestedSeatsNotAvailable e) {
                    return e.getResponseEntity();
                }
                /* check if the reservation is successful
                 return response entity object with HTTP status of ok and
                message "reservation made successfully" appending the booking Reference
                if the reservation is not successful
                return response entity object with HTTP status of bad request */
                if (result) {
                    responseEntity = new ResponseEntity<>("Reservation made successfully. Booking Reference: " + reservation.getBookingReference(), HttpStatus.OK);
                }
                else {
                    responseEntity = new ResponseEntity<>("Reservation failed", HttpStatus.BAD_REQUEST);
                }

            }
        } catch (AuthenticationException e) {
            responseEntity = e.getResponseEntity();
        }
        return responseEntity;
    }
}

