package com.jonatasalmeidadev.cancunbookingapi.repository;

import com.jonatasalmeidadev.cancunbookingapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
