package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.ReservationRequestDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    /** 내 주차장 예약 알림
     *
     * @param reservationRequestDto 예약 요청 DTO
     * @return "success"
     */
    public String sendReservationSuccessPush(ReservationRequestDto reservationRequestDto) {

        return "success";
    }

    /** 토지대장 허가 확인 알림
     *
     * @return "success"
     */
    public String sendGrantSuccessPush() {
        return "success";
    }

    /**
     * 예약 시간 5분 전 알림
     *
     * @return
     */
    @Scheduled(cron = "0 */1 * * * *")
    public String sendBeforeReservationTime() {
        return "success";
    }

    /** 예약 시간 만료 5분 전 알림
     *
     * @return
     */
    @Scheduled(cron = "0 */1 * * * *")
    public String sendBeforeEndReservationTime() {
        return "success";
    }

}
