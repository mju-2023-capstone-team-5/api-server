package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ParkingLotService {

    // Repository 객체
    private final ParkingLotRepository parkingLotRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional
    public ParkingLotResponseDto getParkingLotInfo(Long id) {
        Optional<ParkingLot> result = parkingLotRepository.findById(id);
        ParkingLot parkingLot = result.get();

        ParkingLotResponseDto dto = ParkingLotResponseDto.builder()
                .address(parkingLot.getAddress())
                .name(parkingLot.getName())
                .build();

        return dto;
    }

    // 주차장 생성
    public ParkingLotResponseDto createParkingLot(ParkingLotRequestDto requestDto) {

        // ParkingLot 엔티티를 데이터베이스에 저장

        ParkingLot savedParkingLot = parkingLotRepository.save(requestDto.ToEntity());

        ParkingLotResponseDto responseDto = new ParkingLotResponseDto();
        responseDto.setParkingLotId(Math.toIntExact(savedParkingLot.getParkingLotId()));
        responseDto.setName(savedParkingLot.getName());
        responseDto.setAddress(savedParkingLot.getAddress());
        responseDto.setLatitude(savedParkingLot.getLatitude());
        responseDto.setLongitude(savedParkingLot.getLongitude());
        responseDto.setTotalSpace(savedParkingLot.getTotalSpace());
        responseDto.setRemainingSpace(savedParkingLot.getRemainingSpace());
        responseDto.setOpenTime(savedParkingLot.getOpenTime());
        responseDto.setStatus(savedParkingLot.getStatus());
        responseDto.setFreeInformation(savedParkingLot.getFreeInformation());

        return responseDto;
    }

    // 주차장 업데이트
    public ParkingLotResponseDto updateParkingLot(Long id, ParkingLotRequestDto requestDto) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        // 주차장 상세 정보를 업데이트합니다.
        parkingLot.setName(requestDto.getName());
        parkingLot.setAddress(requestDto.getAddress());
        parkingLot.setLatitude(requestDto.getLatitude());
        parkingLot.setLongitude(requestDto.getLongitude());
        parkingLot.setTotalSpace(requestDto.getTotalSpace());
        parkingLot.setRemainingSpace(requestDto.getRemainingSpace());
        parkingLot.setStatus(requestDto.getStatus());
        parkingLot.setOpenTime(requestDto.getOpenTime());
        parkingLot.setFreeInformation(requestDto.getFreeInformation());


        ParkingLot updatedParkingLot = parkingLotRepository.save(parkingLot);
        return mapToResponseDto(updatedParkingLot);
    }

    private ParkingLotResponseDto mapToResponseDto(ParkingLot parkingLot) {
        ParkingLotResponseDto responseDto = new ParkingLotResponseDto();
        responseDto.setParkingLotId(Math.toIntExact(parkingLot.getParkingLotId()));
        responseDto.setName(parkingLot.getName());
        responseDto.setAddress(parkingLot.getAddress());
        responseDto.setLatitude(parkingLot.getLatitude());
        responseDto.setLongitude(parkingLot.getLongitude());
        responseDto.setTotalSpace(parkingLot.getTotalSpace());
        responseDto.setRemainingSpace(parkingLot.getRemainingSpace());
        responseDto.setStatus(parkingLot.getStatus());
        responseDto.setOpenTime(parkingLot.getOpenTime());
        responseDto.setFreeInformation(parkingLot.getFreeInformation());


        return responseDto;
    }


    public void deleteParkingLot(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
