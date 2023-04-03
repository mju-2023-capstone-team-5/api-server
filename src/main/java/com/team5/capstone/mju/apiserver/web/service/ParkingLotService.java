package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ParkingLotService {

    // Repository 객체
    private final ParkingLotRepository parkingLotRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public ParkingLot getParkingLotInfo(Long id) {
        Optional<ParkingLot> result = parkingLotRepository.findById(id);
        return result.orElseThrow(() -> new IllegalStateException("잘못된 아이디"));
    }
}
