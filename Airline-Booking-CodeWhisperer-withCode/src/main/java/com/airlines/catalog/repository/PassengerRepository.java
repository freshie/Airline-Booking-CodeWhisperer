package com.airlines.catalog.repository;

import com.airlines.catalog.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/* create jpa repository interface PassengerRepository.
Add a method to save Passenger. */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    Passenger save(Passenger passenger);
}
