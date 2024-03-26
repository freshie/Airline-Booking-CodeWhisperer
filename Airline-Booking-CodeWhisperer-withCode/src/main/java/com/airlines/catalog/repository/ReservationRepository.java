package com.airlines.catalog.repository;

import com.airlines.catalog.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Create interface ReservationRepository that extends JpaRepository.
// Add a method to save reservation.
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation save(Reservation reservation);
}

