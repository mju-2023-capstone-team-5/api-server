package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.*;
import com.team5.capstone.mju.apiserver.web.entity.*;
import com.team5.capstone.mju.apiserver.web.enums.UserDefaultPoint;
import com.team5.capstone.mju.apiserver.web.enums.UserPayReceiptType;
import com.team5.capstone.mju.apiserver.web.exceptions.*;
import com.team5.capstone.mju.apiserver.web.repository.*;
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
    private final UserPayReceiptRepository userPayReceiptRepository;
    private final HistoryRepository historyRepository;

    private final ParkingLotService parkingLotService;
    private final ParkingLotRepository parkingLotRepository;

    public UserService(UserRepository userRepository, ParkingLotOwnerRepository ownerRepository, UserPointRepository userPointRepository, UserPayReceiptRepository userPayReceiptRepository, HistoryRepository historyRepository, ParkingLotService parkingLotService,
                       ParkingLotRepository parkingLotRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.userPointRepository = userPointRepository;
        this.userPayReceiptRepository = userPayReceiptRepository;
        this.historyRepository = historyRepository;
        this.parkingLotService = parkingLotService;
        this.parkingLotRepository = parkingLotRepository;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserPoint foundPoint = userPointRepository.findByUserId(Math.toIntExact(found.getUserid()))
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저의 포인트가 존재하지 않습니다."));
        return UserResponseDto.of(found, foundPoint);
    }

    @Transactional(readOnly = true)
    public OwnerResponseDto getParkingLotOwnerInfo(Long id) {
        ParkingLotOwner found = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));
        User foundUser = userRepository.findById(Long.valueOf(found.getOwnerUserId()))
                .orElseThrow(() -> new UserNotFoundException(found.getOwnerUserId()));

        return OwnerResponseDto.of(found, foundUser);

    }

    // 사용자를 삭제하는 메소드
    @Transactional
    public void deleteUser(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(found);
    }

    // 사용자를 삭제하는 메소드
    // 카카오 계정 관리 페이지나 고객센터에서 연결끊기를 진행 시 넘어오는 정보는 kakaoId이기 때문에 kakaoId를 이용한 삭제처리
    @Transactional
    public void deleteUserFromKakaoId(Long kakaoId) {
        User found = userRepository.findByKakaoAppUuid(String.valueOf(kakaoId))
                .orElseThrow(() -> new KakaoSocialLoginUserNotFoundException(kakaoId));
        userRepository.delete(found);
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponseDto> getMyAllParkingLots(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        List<ParkingLotOwner> all = ownerRepository.findAllByOwnerUserId(Math.toIntExact(id));

        List<ParkingLotResponseDto> resultDtos = new ArrayList<>();

        all.forEach(owner -> {
            resultDtos.add(parkingLotService.getParkingLotInfo(Long.valueOf(owner.getParkingLotId())));
        });

        return resultDtos;
    }

    @Transactional
    public UserPointReceiptResponseDto usePoint(Long id, Long amount) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserPoint foundPoint = userPointRepository.findByUserId(Math.toIntExact(found.getUserid()))
                .orElseThrow(() -> new UserPointNotFoundException(found.getUserid()));

        foundPoint.use(amount);

        UserPayReceipt savedReceipt = writeUserPayReceipt(found.getUserid(), amount, UserPayReceiptType.POINT_USED.getType());
        return UserPointReceiptResponseDto.of(found, foundPoint, savedReceipt);
    }

    @Transactional
    public UserPointReceiptResponseDto earnPoint(Long id, Long amount) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserPoint foundPoint = userPointRepository.findByUserId(Math.toIntExact(found.getUserid()))
                .orElseThrow(() -> new UserPointNotFoundException(found.getUserid()));

        foundPoint.earn(amount);

        UserPayReceipt savedReceipt = writeUserPayReceipt(found.getUserid(), amount, UserPayReceiptType.POINT_EARN.getType());
        return UserPointReceiptResponseDto.of(found, foundPoint, savedReceipt);
    }

    @Transactional
    public UserPointReceiptResponseDto earnPointToOwner(Long id, Long amount) {
        ParkingLotOwner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(id));

        return earnPoint(Long.valueOf(owner.getOwnerUserId()), amount);
    }

    @Transactional
    public void cancelEarnPoint(Long id, Long amount) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserPoint foundPoint = userPointRepository.findByUserId(Math.toIntExact(found.getUserid()))
                .orElseThrow(() -> new UserPointNotFoundException(found.getUserid()));

        foundPoint.use(amount);
    }

    @Transactional
    public void cancelUsePoint(Long id, Long amount) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserPoint foundPoint = userPointRepository.findByUserId(Math.toIntExact(found.getUserid()))
                .orElseThrow(() -> new UserPointNotFoundException(found.getUserid()));

        foundPoint.earn(amount);
    }



    @Transactional
    public UserPointResponseDto createAndInitPoint(Long id) {
        userPointRepository.findByUserId(Math.toIntExact(id))
                .ifPresent((point) -> {
                    throw new UserPointAlreadyExistException();
                });
        UserPoint userPoint = new UserPoint();
        userPoint.setPoints(UserDefaultPoint.DEMO.getAmount());
        userPoint.setUserId(Math.toIntExact(id));

        UserPoint saved = userPointRepository.save(userPoint);
        return UserPointResponseDto.of(saved);
    }

    @Transactional
    public UserPayReceipt writeUserPayReceipt(Long userId, Long amount, String paymentType) {
        UserPayReceipt receipt = new UserPayReceipt();
        receipt.setUserId(Math.toIntExact(userId));
        receipt.setAmount(amount);
        receipt.setPaymentType(paymentType);

        UserPayReceipt saved = userPayReceiptRepository.save(receipt);

        return saved;
    }

    @Transactional
    public String addFcmToken(Long userId, UserAddFcmTokenRequestDTO requestDTO) {
        User found = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        found.addFcmTokenSelf(requestDTO);
        return "success";
    }

    @Transactional(readOnly = true)
    public List<HistoryResponseDto> getAllMyHistory(Long userId) {
        List<History> all = historyRepository.findAllByUserId(Math.toIntExact(userId));

        List<HistoryResponseDto> responseDtos = new ArrayList<>();
        all.forEach(history -> {
            ParkingLot parkingLot = parkingLotRepository.findById(Long.valueOf(history.getParkingLotId())).get();
            responseDtos.add(HistoryResponseDto.of(history, parkingLot));
        });

        return responseDtos;
    }
}
