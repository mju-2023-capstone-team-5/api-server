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

    @Operation(summary = "예약 내역 반환 API", description = "예약 내역 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 내역 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "예약 내역 정보가 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/history/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<HistoryResponseDto> getHistory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(historyService.getHistoryInfo(id));
    }

//    @Operation(summary = "예약 내역 생성 API", description = "예약 정보를 받아 새로운 예약 정보를 생성하는 API",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "새로운 예약 정보 생성에 성공"),
//                    @ApiResponse(responseCode = "404", description = "사용자 또는 주차장 정보가 존재하지 않아 예약 정보 생성에 실패")
//            }
//    )
//    @PostMapping("/history")
//    public ResponseEntity<HistoryResponseDto> createHistory(@RequestBody HistoryRequestDto requestDto) {
//        log.info(requestDto.toString());
//        HistoryResponseDto responseDto = historyService.createHistory(requestDto);
//        return ResponseEntity.ok(responseDto);
//    }

//    @Operation(summary = "예약 내역 삭제 API", description = "예약 정보 아이디를 받아 내역을 삭제하는 API",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "예약 내역 삭제에 성공"),
//                    @ApiResponse(responseCode = "404", description = "예약 내역 정보가 존재하지 않아 조회에 실패")
//            }
//    )
//    @DeleteMapping("/history/{id}")
//    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
//        historyService.deleteHistory(id);
//        return ResponseEntity.noContent().build();
//    }
}
