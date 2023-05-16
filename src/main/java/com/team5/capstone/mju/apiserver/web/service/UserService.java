package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.*;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.entity.UserPoint;
import com.team5.capstone.mju.apiserver.web.enums.UserDefaultPoint;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotOwnerRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserPointRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ParkingLotOwnerRepository ownerRepository;
    private final UserPointRepository userPointRepository;

    private final ParkingLotService parkingLotService;

    public UserService(UserRepository userRepository, ParkingLotOwnerRepository ownerRepository, UserPointRepository userPointRepository, ParkingLotService parkingLotService) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.userPointRepository = userPointRepository;
        this.parkingLotService = parkingLotService;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));

        return UserResponseDto.of(found);
    }

    @Transactional(readOnly = true)
    public OwnerResponseDto getParkingLotOwnerInfo(Long id) {
        ParkingLotOwner found = ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
        User foundUser = userRepository.findById(Long.valueOf(found.getOwnerId()))
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));

        return OwnerResponseDto.of(found, foundUser);

    }

    // 사용자를 삭제하는 메소드
    @Transactional
    public void deleteUser(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
        userRepository.delete(found);
    }

    // 사용자를 삭제하는 메소드
    // 카카오 계정 관리 페이지나 고객센터에서 연결끊기를 진행 시 넘어오는 정보는 kakaoId이기 때문에 kakaoId를 이용한 삭제처리
    @Transactional
    public void deleteUserFromKakaoId(Long kakaoId) {
        User found = userRepository.findByKakaoAppUuid(String.valueOf(kakaoId))
                .orElseThrow(() -> new EntityNotFoundException("해당하는 카카오 유저가 없습니다."));
        userRepository.delete(found);
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponseDto> getMyAllParkingLots(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        List<ParkingLotOwner> all = ownerRepository.findAllByOwnerId(Math.toIntExact(id));

        List<ParkingLotResponseDto> resultDtos = new ArrayList<>();

        all.forEach(owner -> {
            resultDtos.add(parkingLotService.getParkingLotInfo(Long.valueOf(owner.getParkingLotId())));
        });

        return resultDtos;
    }

    @Transactional
    public UserResponseDto usePoint(Long id) {
        return null;
    }

    @Transactional
    public UserResponseDto earnPoint(Long id) {
        return null;
    }

    @Transactional
    public UserPointResponseDto createAndInitPoint(Long id) {
        userPointRepository.findByUserId(Math.toIntExact(id))
                .ifPresent((point) -> {
                    throw new RuntimeException("사용자에 대한 포인트가 이미 존재합니다.");
                });
        UserPoint userPoint = new UserPoint();
        userPoint.setPoints(UserDefaultPoint.DEMO.getAmount());
        userPoint.setUserId(Math.toIntExact(id));

        UserPoint saved = userPointRepository.save(userPoint);
        return UserPointResponseDto.of(saved);
    }

}
