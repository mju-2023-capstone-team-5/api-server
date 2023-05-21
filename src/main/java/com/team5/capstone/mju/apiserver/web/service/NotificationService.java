package com.team5.capstone.mju.apiserver.web.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLotOwner;
import com.team5.capstone.mju.apiserver.web.entity.Reservation;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.enums.ParkingLotPriceType;
import com.team5.capstone.mju.apiserver.web.exceptions.OwnerNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.ParkingLotNotFoundException;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotOwnerRepository;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.repository.ReservationRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final ParkingLotOwnerRepository ownerRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ReservationRepository reservationRepository;

    public NotificationService(UserRepository userRepository, ParkingLotOwnerRepository ownerRepository, ParkingLotRepository parkingLotRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.reservationRepository = reservationRepository;
    }

    /** 내 주차장 예약 알림
     *
     * @param reservationRequestDto 예약 요청 DTO
     * @return "success"
     */

    @Transactional(readOnly = true)
    public String sendReservationSuccessPush(ReservationRequestDto reservationRequestDto) throws FirebaseMessagingException {
        ParkingLot foundParkingLot = parkingLotRepository.findById(Long.valueOf(reservationRequestDto.getParkingLotId()))
                .orElseThrow(() -> new ParkingLotNotFoundException(reservationRequestDto.getParkingLotId()));
        ParkingLotOwner foundOwner = ownerRepository.findById(Long.valueOf(foundParkingLot.getOwnerId()))
                .orElseThrow(() -> new OwnerNotFoundException(foundParkingLot.getOwnerId()));
        String fcmToken = userRepository.findById(Long.valueOf(foundOwner.getOwnerUserId()))
                .get()
                .getFcmToken();

        Message message = buildFireBaseMessage("내 주차장 예약 알림", "사용자의 주차장 예약이 완료되었습니다.", fcmToken);
        String response = FirebaseMessaging.getInstance().send(message);

        return "success";
    }

    /**
     * 토지대장 허가 확인 알림
     *
     * @return "success"
     */
    @Transactional(readOnly = true)
    public String sendGrantSuccessPush(ParkingLotDto parkingLotDto) throws FirebaseMessagingException {
        ParkingLot foundParkingLot = parkingLotRepository.findById(Long.valueOf(parkingLotDto.getId()))
                .orElseThrow(() -> new ParkingLotNotFoundException(parkingLotDto.getId()));
        ParkingLotOwner foundOwner = ownerRepository.findById(Long.valueOf(foundParkingLot.getOwnerId()))
                .get();

        String fcmToken = userRepository.findById(Long.valueOf(foundOwner.getOwnerUserId()))
                .get()
                .getFcmToken();

        Message message = buildFireBaseMessage("토지대장 허가 확인 알림", "사용자의 주차장 등록 요청이 허가되었습니다.", fcmToken);
        String response = FirebaseMessaging.getInstance().send(message);

        return "success";
    }

    /**
     * 예약 시간 5분 전 알림
     *
     * @return
     */
    @Scheduled(cron = "0 */1 * * * *")
    @Transactional(readOnly = true)
    public void sendBeforeReservationTime() {
        List<Reservation> all = reservationRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        all.forEach(reservation -> {
            if (now.isAfter(reservation.getDate().minusMinutes(6))) {
                String fcmToken = userRepository.findById(Long.valueOf(reservation.getUserId()))
                        .get()
                        .getFcmToken();

                Message message = buildFireBaseMessage("예약 시간 5분 전 알림", "사용자가 예약한 주차장의 예약 시작 시간이 5분 남았습니다.", fcmToken);
                try {
                    String response = FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /** 예약 시간 만료 5분 전 알림
     *
     * @return
     */
    @Scheduled(cron = "0 */1 * * * *")
    @Transactional(readOnly = true)
    public void sendBeforeEndReservationTime() {
        List<Reservation> all = reservationRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        all.forEach(reservation -> {
            LocalDateTime targetDate = null;
            if (reservation.getDateType().equals(ParkingLotPriceType.HOUR.getType())) {
                targetDate = reservation.getDate()
                        .plusHours(reservation.getDuration())
                        .minusMinutes(6);
            }
            else {
                targetDate = reservation.getDate()
                        .plusMonths(reservation.getDuration())
                        .minusMinutes(6);
            }

            if (now.isAfter(targetDate)) {
                String fcmToken = userRepository.findById(Long.valueOf(reservation.getUserId()))
                        .get()
                        .getFcmToken();

                Message message = buildFireBaseMessage("예약 만료 시간 5분 전 알림", "사용자가 예약한 주차장의 예약 만료 시간이 5분 남았습니다.", fcmToken);
                try {
                    String response = FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Message buildFireBaseMessage(String title, String body, String fcmToken) {
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(fcmToken)
                .build();
    }

}
