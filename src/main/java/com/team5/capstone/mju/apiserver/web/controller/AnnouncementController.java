package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.AnnouncementRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.AnnouncementResponseDto;
import com.team5.capstone.mju.apiserver.web.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "공지사항 Controller", description = "공지사항 관련 API 요청 Controller")
// 현재까지의 API 요청 url: localhost:8080 + /api/v1
public class AnnouncementController {

    //서비스 객체
    private final AnnouncementService announcementService;

    @Autowired // 생성자를 통한 의존성 주입
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Operation(summary = "공지사항 List 반환 API", description = "공지사항 List를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지사항 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "공지사항이 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/announcement")
    public ResponseEntity<List<AnnouncementResponseDto>> getAnnouncement() {
        List<AnnouncementResponseDto> announcementList = (List<AnnouncementResponseDto>) announcementService.getAnnouncementInfoList();
        return ResponseEntity.ok(announcementList);
    }

    @Operation(summary = "공지사항 반환 API", description = "공지사항 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지사항 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "공지사항이 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/announcement/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/announcement/1이면 id 변수가 1
    public ResponseEntity<AnnouncementResponseDto> getAnnouncement(@PathVariable("id") Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncementInfo(id));
    }

    @Operation(summary = "공지사항 생성 API", description = "공지사항 내용을 받아 새로운 공지사항을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 공지사항 생성에 성공"),
                    @ApiResponse(responseCode = "404", description = "공지사항 정보가 존재하지 않아 예약 정보 생성에 실패")
            }
    )
    @PostMapping("/announcement")
    public ResponseEntity<AnnouncementResponseDto> createAnnouncement(@RequestBody AnnouncementRequestDto requestDto) {
        AnnouncementResponseDto responseDto = announcementService.createAnnouncement(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "공지사항 업데이트 API", description = "공지사항 내용을 받아 공지사항을 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "공지사항 업데이트에 성공"),
                    @ApiResponse(responseCode = "404", description = "공지사항이 존재하지 않아 업데이트에 실패")
            }
    )
    @PatchMapping("/announcement/{id}")
    public ResponseEntity<AnnouncementResponseDto> updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementRequestDto requestDto) {
        AnnouncementResponseDto responseDto = announcementService.updateAnnouncement(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "공지사항 삭제 API", description = "공지사항 아이디를 받아 내역을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 내역 삭제에 성공"),
                    @ApiResponse(responseCode = "404", description = "예약 내역 정보가 존재하지 않아 조회에 실패")
            }
    )
    @DeleteMapping("/announcement/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
