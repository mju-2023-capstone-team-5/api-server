package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotStatus;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ParkingLotService {

    // Repository 객체
    private final ParkingLotRepository parkingLotRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional(readOnly = true)
    public ParkingLotResponseDto getParkingLotInfo(Long id) {
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        return ParkingLotResponseDto.of(found);
    }

    // 주차장 생성
    @Transactional
    public ParkingLotResponseDto createParkingLot(ParkingLotRequestDto requestDto) {
        // ParkingLot 엔티티를 데이터베이스에 저장
        requestDto.setStatus(ParkingLotStatus.WAIT.getStatus());
        ParkingLot savedParkingLot = parkingLotRepository.save(requestDto.toEntity());
        return ParkingLotResponseDto.of(savedParkingLot);
    }

    // 주차장 업데이트
    @Transactional
    public ParkingLotResponseDto updateParkingLot(Long id, ParkingLotRequestDto requestDto) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        // 주차장 상세 정보를 업데이트합니다.
        parkingLot.updateAllInfoSelf(requestDto);
        ParkingLot updatedParkingLot = parkingLotRepository.save(parkingLot);
        return ParkingLotResponseDto.of(updatedParkingLot);
    }

    @Transactional
    public void updateParkingLotStatus(Long id) {
        parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."))
                .updateStatusToParkingAvailableSelf(); // WAIT status인 경우 스스로 ParkingAvailable status로 변경
    }

    @Transactional
    public void addImageUrl(Long id, String url) {
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));
        found.setImageUrl(url);
    }
    
    @Transactional
    public void deleteParkingLot(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
