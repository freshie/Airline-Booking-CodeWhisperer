package com.airlines.catalog.service;

import com.airlines.catalog.exception.FlightNotFoundException;
import com.airlines.catalog.exception.RequestedSeatsNotAvailable;
import com.airlines.catalog.model.Flight;
import com.airlines.catalog.model.Passenger;
import com.airlines.catalog.model.Reservation;
import com.airlines.catalog.repository.FlightRepository;
import com.airlines.catalog.repository.PassengerRepository;
import com.airlines.catalog.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class FlightBooking {

    /* create a method send message with sns arn and AWS Region as input parameter*/
    public void sendMessage(String message, String arn, Region region) {
        SnsClient snsClient = SnsClient.builder()
                .region(region)
                .build();

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(arn)
                .build();

        PublishResponse result = snsClient.publish(request);
        System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        snsClient.close();


    }

    /*Create reserveFlight method with passenger, reservation, passengerRepository,
reservationRepository,flightresultsRepository, number of passengers as input parameters.
Method should return a Boolean output
Get the flight details by calling findById method from FlightresultsRepository with
flightId as input parameter
if no result is null throw FlightNotFoundException
If number of seats is less than number of passengers throw RequestedSeatsNotAvailable exception with
flightId as input parameter
Call getseatAvailable from flight object to get the available seats
If seats available is less than number of passengers then return false
If seats available is greater than or equal to number of passengers then
    decrease the seats available attribute in flight object by calling setseatAvailable method
    Save the passenger and get the passenger id
    update the reservation object with the passenger id
    save the reservation details
    call sendMessage by passing reservation json string as input parameter
    Method should return True or False based on the result of save methods
All of the above steps should be done within a transaction.
 */
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean reserveFlight(Passenger passenger, Reservation reservation,
                                 PassengerRepository passengerRepository, ReservationRepository reservationRepository, FlightRepository flightRepository,
                                 int numberOfPassengers, String topicArn, Region region) throws FlightNotFoundException, RequestedSeatsNotAvailable {
        Flight flight = flightRepository.findById(reservation.getFlightId());
        if (flight == null) {
            throw new FlightNotFoundException(reservation.getFlightId());
        }
        if (flight.getSeatAvailable() < numberOfPassengers) {
            throw new RequestedSeatsNotAvailable();
        }
        flight.setSeatAvailable(flight.getSeatAvailable() - numberOfPassengers);
        flightRepository.save(flight);
        Passenger passenger1 = passengerRepository.save(passenger);
        reservation.setPassengerId(passenger1.getPassengerId());
        reservationRepository.save(reservation);
        sendMessage(reservation.toString(), topicArn, region);
        return true;

    }
}




