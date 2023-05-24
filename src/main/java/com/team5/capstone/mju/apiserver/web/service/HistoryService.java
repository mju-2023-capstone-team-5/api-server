package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.HistoryRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.HistoryResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.exceptions.HistoryNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.ParkingLotNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.UserNotFoundException;
import com.team5.capstone.mju.apiserver.web.repository.HistoryRepository;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import com.team5.capstone.mju.apiserver.web.vo.ReservationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class HistoryService {

    // Repository 객체
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public HistoryService(HistoryRepository historyRepository,
                          UserRepository userRepository,
                          ParkingLotRepository parkingLotRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional
    public HistoryResponseDto getHistoryInfo(Long id) {
        History found = historyRepository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException(id));

        HistoryResponseDto dto = HistoryResponseDto.builder()
                .historyId(Math.toIntExact(found.getHistoryId()))
                .userId(found.getUserId())
                .parkingLotId(found.getParkingLotId())
                .startTime(found.getStartTime())
                .endTime(found.getEndTime())
//                .dateUsed(found.getDateUsed())
                .build();

        return dto;
    }

    // 이용내역 생성
    @Transactional
    public void createHistory(ReservationRequestDto requestDto, Long reservationId) {

        // History 엔티티를 데이터베이스에 저장
        userRepository.findById(Long.valueOf(requestDto.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));

        parkingLotRepository.findById(Long.valueOf(requestDto.getParkingLotId()))
                .orElseThrow(() -> new ParkingLotNotFoundException(requestDto.getUserId()));

        History savedHistory = historyRepository.save(History.of(requestDto, reservationId));
    }

    //이용내역 삭제
    @Transactional
    public void deleteHistory(Long reservationId) {
        List<History> all = historyRepository.findAllByReservationId(Math.toIntExact(reservationId));
        historyRepository.deleteAll(all);
    }
}
