package com.airlines.catalog.repository;

import com.airlines.catalog.model.Airport;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/* Create JPA repository interface named AirportRepository.
Add a method to find airport by airport code */
@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    Airport findByAirportCode(String airportCode);
}