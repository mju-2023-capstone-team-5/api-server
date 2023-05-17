package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestOldDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseOldDto;
import com.team5.capstone.mju.apiserver.web.entity.*;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotStatus;
import com.team5.capstone.mju.apiserver.web.repository.*;
import com.team5.capstone.mju.apiserver.web.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class ParkingLotService {

    @Autowired
    private MapUtil mapUtil;

    // Repository 객체
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingAvailableTimeRepository availableTimeRepository;
    private final ParkingPriceRepository priceRepository;
    private final ParkingLotOwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingAvailableTimeRepository availableTimeRepository, ParkingPriceRepository priceRepository, ParkingLotOwnerRepository ownerRepository, UserRepository userRepository,
                             RatingRepository ratingRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.availableTimeRepository = availableTimeRepository;
        this.priceRepository = priceRepository;
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional(readOnly = true)
    public ParkingLotResponseDto getParkingLotInfo(Long id) {
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));

        ParkingLotOwner owner = ownerRepository.findByParkingLotId(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("주차장 주인 정보를 찾을 수 없습니다"));
        Optional<Rating> rating = ratingRepository.findByParkingLotId(Math.toIntExact(id));
        List<ParkingAvailableTime> availableTimeList = availableTimeRepository.findAllByParkingLotId(Math.toIntExact(id));
        List<ParkingPrice> priceList = priceRepository.findAllByParkingLotId(Math.toIntExact(id));

        return ParkingLotResponseDto.of(ParkingLotDto.of(found, owner, rating, availableTimeList, priceList));
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponseDto> getParkingLotsInRectangle(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
        List<ParkingLot> all = parkingLotRepository.findAll();

        List<ParkingLot> found = all.stream()
                .filter(parkingLot -> {
                    return mapUtil.isInsideRectangle(parkingLot.getLatitude(), parkingLot.getLongitude(), x1, y1, x2, y2);
                })
                .collect(Collectors.toList());

        List<ParkingLotResponseDto> resultDtos = new ArrayList<>();

        found.forEach(parkingLot -> {
            resultDtos.add(getParkingLotInfo(parkingLot.getParkingLotId()));
        });

        return resultDtos;
    }

    // 주차장 생성
    @Transactional
    public ParkingLotResponseDto createParkingLot(ParkingLotDto requestDto) {

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

        return getParkingLotInfo(saved.getParkingLotId());
    }

    // 주차장 업데이트
    @Transactional
    public ParkingLotDto updateParkingLot(Long id, ParkingLotDto requestDto) {
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));
        ParkingLot requestParkingLot = requestDto.parseToParkingLot();

        // 주차장 상세 정보를 업데이트합니다.
        found.updateAllInfoSelf(requestParkingLot); // 더티 체킹을 통한 self update

        // owner를 업데이트
        ParkingLotOwner requestOwner = requestDto.parseToParkingLotOwner();
        requestOwner.setParkingLotId(Math.toIntExact(found.getParkingLotId()));
        ParkingLotOwner foundOwner = ownerRepository.findByParkingLotId(Math.toIntExact(id)).get();
        foundOwner.updateAllSelf(requestOwner);

        // price 정보를 업데이트
        Optional<ParkingPrice> foundOldHour = Optional.ofNullable(priceRepository.findByParkingLotIdAndDateType(Math.toIntExact(id), ParkingLotPriceType.HOUR.getType()));
        foundOldHour.ifPresent(priceRepository::delete);
        Optional<ParkingPrice> foundOldMonth = Optional.ofNullable(priceRepository.findByParkingLotIdAndDateType(Math.toIntExact(id), ParkingLotPriceType.MONTH.getType()));
        foundOldMonth.ifPresent(priceRepository::delete);

        Optional<ParkingPrice> foundNewHourOptional = requestDto.parseHourlyToParkingLotPrice();
        foundNewHourOptional.ifPresent(foundNewHour -> {
            foundNewHour.setParkingLotId(Math.toIntExact(id));
            priceRepository.save(foundNewHour);
        });
        Optional<ParkingPrice> foundNewMonthOptional = requestDto.parseMonthlyToParkingLotPrice();
        foundNewMonthOptional.ifPresent(foundNewMonth -> {
            foundNewMonth.setParkingLotId(Math.toIntExact(id));
            priceRepository.save(foundNewMonth);
        });

        // time 정보를 업데이트
        List<ParkingAvailableTime> foundOldTimeList = availableTimeRepository.findAllByParkingLotId(Math.toIntExact(id));
        availableTimeRepository.deleteAll(foundOldTimeList);

        List<ParkingAvailableTime> newAvailableList = requestDto.parseToParkingAvailableTimeList();
        newAvailableList.forEach(available -> {
            available.setParkingLotId(Math.toIntExact(id));
        });
        availableTimeRepository.saveAll(newAvailableList);

        return requestDto;
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
        ParkingLot found = parkingLotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("주차장을 찾을 수 없습니다."));
        parkingLotRepository.delete(found);
    }

    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void permitForDemo() {
        List<ParkingLot> allByStatus = parkingLotRepository.findAllByStatus(ParkingLotStatus.WAIT.getStatus());

        allByStatus.forEach(parkingLot -> {
            parkingLot.updateStatusToParkingAvailableSelf();
        });
    }
}
