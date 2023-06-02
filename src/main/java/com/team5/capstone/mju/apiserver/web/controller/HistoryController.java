package com.team5.capstone.mju.apiserver.web.controller;
import com.team5.capstone.mju.apiserver.web.dto.HistoryRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.HistoryResponseDto;
import com.team5.capstone.mju.apiserver.web.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "내역 Controller", description = "예약 내역 관련 API 요청 Controller")
// 현재까지의 API 요청 url: localhost:8080 + /api/v1
public class HistoryController {

    //서비스 객체
    private final HistoryService historyService;

    @Autowired // 생성자를 통한 의존성 주입
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

}
