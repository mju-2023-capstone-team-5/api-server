package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestOldDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseOldDto;
import com.team5.capstone.mju.apiserver.web.entity.*;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotStatus;
import com.team5.capstone.mju.apiserver.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ParkingLotService {

    // Repository 객체
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingAvailableTimeRepository availableTimeRepository;
    private final ParkingPriceRepository priceRepository;
    private final ParkingLotOwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingAvailableTimeRepository availableTimeRepository, ParkingPriceRepository priceRepository, ParkingLotOwnerRepository ownerRepository, UserRepository userRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.availableTimeRepository = availableTimeRepository;
        this.priceRepository = priceRepository;
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ParkingLotResponseOldDto getParkingLotInfo(Long id) {
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        return ParkingLotResponseOldDto.of(found);
    }

    // 주차장 생성
    @Transactional
    public ParkingLotDto createParkingLot(ParkingLotDto requestDto) {

        User ownerUser = userRepository.findById(Long.valueOf(requestDto.getOwnerId()))
                .orElseThrow(() -> new EntityNotFoundException("주차장 주인 사용자가 존재하지 않습니다."));

        // ParkingLot 엔티티를 데이터베이스에 저장
        ParkingLot parkingLot = requestDto.parseToParkingLot();
        parkingLot.setStatus(ParkingLotStatus.WAIT.getStatus());

        ParkingLot saved = parkingLotRepository.save(parkingLot);

        // Owner을 생성해 데이터베이스에 저장
        ParkingLotOwner parkingLotOwner = requestDto.parseToParkingLotOwner();
        parkingLotOwner.setParkingLotId(Math.toIntExact(saved.getParkingLotId()));

        ParkingLotOwner savedOwner = ownerRepository.save(parkingLotOwner);

        // 더티 체킹을 통한 owner 지정
        saved.setOwnerId(savedOwner.getOwnerId());

        // Price를 생성해 존재하는 Price는 데이터베이스에 저장
        requestDto.parseMonthlyToParkingLotPrice()
                .ifPresent(monthly -> {
                    monthly.setParkingLotId(Math.toIntExact(saved.getParkingLotId()));
                    priceRepository.save(monthly);
                });
        requestDto.parseHourlyToParkingLotPrice()
                .ifPresent(hourly -> {
                    hourly.setParkingLotId(Math.toIntExact(saved.getParkingLotId()));
                    priceRepository.save(hourly);
                });

        // availableTime 배열을 가져와 데이터베이스에 모두 저장
        List<ParkingAvailableTime> availableTimeList = requestDto.parseToParkingAvailableTimeList();
        availableTimeList.forEach(available -> {
            available.setParkingLotId(Math.toIntExact(saved.getParkingLotId()));
        });
        availableTimeRepository.saveAll(availableTimeList);

        return requestDto;
    }

    // 주차장 업데이트
    @Transactional
    public ParkingLotResponseOldDto updateParkingLot(Long id, ParkingLotRequestOldDto requestDto) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        // 주차장 상세 정보를 업데이트합니다.
        parkingLot.updateAllInfoSelf(requestDto); // 더티 체킹을 통한 self update
        return ParkingLotResponseOldDto.of(parkingLot); // update 된 내용을 기반으로 한 DTO 생성 후 반환
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
