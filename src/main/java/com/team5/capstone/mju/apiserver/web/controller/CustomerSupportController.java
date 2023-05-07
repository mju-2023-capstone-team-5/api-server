package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.QnaRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.QnaResponseDto;
import com.team5.capstone.mju.apiserver.web.service.CustomerSupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "고객지원 Controller", description = "고객지원 관련 API 요청 Controller")
public class CustomerSupportController {
    private final CustomerSupportService customerSupportService;

    @Autowired
    public CustomerSupportController(CustomerSupportService customerSupportService) {
        this.customerSupportService = customerSupportService;
    }

    @Operation(summary = "QnA 정보 반환 API", description = "유저 아이디를 받아 QnA 정보를 리스트로 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "QnA 정보 조회에 성공")
            }
    )
    @GetMapping("/qna/{id}")
    public ResponseEntity<List<QnaResponseDto>> getQna(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerSupportService.getQnaInfo(id));
    }

    @Operation(summary = "QnA 정보 생성 API", description = "QnA 정보를 받아 새로운 예약을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 QnA 생성에 성공")
            }
    )
    @PostMapping("/qna")
    public ResponseEntity<QnaResponseDto> createQna(@RequestBody QnaRequestDto requestDto) {
        log.info(requestDto.toString());
        QnaResponseDto responseDto = customerSupportService.createQna(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "QnA 정보 업데이트 API", description = "QnA 정보를 받아 예약을 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "QnA 정보 업데이트에 성공")
            }
    )
    @PatchMapping("/qna/{id}")
    public ResponseEntity<QnaResponseDto> updateQna(@PathVariable Long id, @RequestBody QnaRequestDto requestDto) {
        log.info(requestDto.toString());
        QnaResponseDto responseDto = customerSupportService.updateQna(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "QnA 정보 삭제 API", description = "QnA 아이디를 받아 예약을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "QnA 삭제에 성공")
            }
    )
    @DeleteMapping("/qna/{id}")
    public ResponseEntity<Void> deleteQna(@PathVariable Long id) {
        customerSupportService.deleteQna(id);
        return ResponseEntity.noContent().build();
    }

}
