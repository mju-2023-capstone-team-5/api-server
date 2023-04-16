package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.HistoryRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.HistoryResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.History;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class HistoryService {

    // Repository 객체
    private final HistoryRepository historyRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public HistoryResponseDto getHistoryInfo(Long id) {
        Optional<History> result = historyRepository.findById(id);
        History history = result.get();

        HistoryResponseDto dto = HistoryResponseDto.builder()
                .historyId(Math.toIntExact(history.getHistoryId()))
                .userId(history.getUserId())
                .parkingLotId(history.getParkingLotId())
                .startTime(history.getStartTime())
                .endTime(history.getEndTime())
                .dateUsed(history.getDateUsed())
                .build();

        return dto;
    }

    // 이용내역 생성
    @Transactional
    public HistoryResponseDto createHistory(HistoryRequestDto requestDto) {

        // History 엔티티를 데이터베이스에 저장

        History savedHistory = historyRepository.save(requestDto.ToEntity());

        HistoryResponseDto responseDto = new HistoryResponseDto();
        responseDto.setHistoryId(Math.toIntExact(savedHistory.getHistoryId()));
        responseDto.setUserId(savedHistory.getUserId());
        responseDto.setParkingLotId(savedHistory.getParkingLotId());
        responseDto.setStartTime(savedHistory.getStartTime());
        responseDto.setEndTime(savedHistory.getEndTime());
        responseDto.setDateUsed(savedHistory.getDateUsed());

        return responseDto;
    }

    //이용내역 삭제
    @Transactional
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}
