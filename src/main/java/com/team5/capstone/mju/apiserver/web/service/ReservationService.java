package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotStatus;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ReservationService {

    // Repository 객체
    private final ReservationRepository reservationRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public ReservationResponseDto getReservationInfo(Long id) {
        Reservation found = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("예약내역을 찾을 수 없습니다."));

        return ReservationResponseDto.of(found);
    }

    // 예약생성
    @Transactional
    public ReservationResponseDto createReservation(ReservationRequestDto requestDto) {
        // Reservation 엔티티를 데이터베이스에 저장
        Reservation savedReservation = reservationRepository.save(requestDto.toEntity());
        return ReservationResponseDto.of(savedReservation);
    }

    // 예약내역 업데이트
    @Transactional
    public ReservationResponseDto updateReservation(Long id, ReservationRequestDto requestDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("예약내역을 찾을 수 없습니다."));

        // 예약 상세 정보를 업데이트합니다.
        reservation.updateAllInfoSelf(requestDto);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationResponseDto.of(updatedReservation);
    }

    // 예약정보 삭제
    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
