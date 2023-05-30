package com.team5.capstone.mju.apiserver.web.repository;

import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByIsReturned(Boolean isReturned);
}