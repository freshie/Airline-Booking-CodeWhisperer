package com.airlines.catalog.repository;

import com.airlines.catalog.model.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/*Create JPA repository interface FlightRepository.
Add a method to find flights by departure date, departure airport code, arrival airport code that returns a iterable flight
Add a method to get flight with Id as parameter and return the flight object
*/
@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    Iterable<Flight> findByDepartureDateAndDepartureAirportCodeAndArrivalAirportCode(String departureDate, String departureAirportCode, String arrivalAirportCode);
    Flight findById(int id);
}


