package com.team5.capstone.mju.apiserver.web.controller;
import com.team5.capstone.mju.apiserver.web.dto.HistoryRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.HistoryResponseDto;
import com.team5.capstone.mju.apiserver.web.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
// 현재까지의 API 요청 url: localhost:8080 + /api/v1
public class HistoryController {

    //서비스 객체
    private final HistoryService historyService;

    @Autowired // 생성자를 통한 의존성 주입
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<HistoryResponseDto> getHistory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(historyService.getHistoryInfo(id));
    }

    @PostMapping("/history")
    public ResponseEntity<HistoryResponseDto> createHistory(@RequestBody HistoryRequestDto requestDto) {
        log.info(requestDto.toString());
        HistoryResponseDto responseDto = historyService.createHistory(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }
}
