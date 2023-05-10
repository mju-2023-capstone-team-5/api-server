package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.GradeRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.GradeResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.ParkingLot;
import com.team5.capstone.mju.apiserver.web.repository.ParkingLotRepository;
import com.team5.capstone.mju.apiserver.web.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "후기&평점 Controller", description = "후기&평점 관련 API 요청 Controller")
public class GradeController {
    private final GradeService gradeService;
    private final ParkingLotRepository parkingLotRepository;

    public GradeController(GradeService gradeService, ParkingLotRepository parkingLotRepository) {
        this.gradeService = gradeService;
        this.parkingLotRepository = parkingLotRepository;
    }


    @Operation(summary = "후기 정보 반환 API", description = "주차장 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "후기 조회에 성공")
            }
    )
    @GetMapping("/grade/parking/{id}")
    public ResponseEntity<List<GradeResponseDto>> getGradeByParkingLotId(@PathVariable("id") Integer id) {
        List<GradeResponseDto> grades = gradeService.getGradeByParkingLotId(Long.valueOf(id));
        return ResponseEntity.ok(grades);
    }

    @Operation(summary = "후기 정보 반환 API", description = "유저 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "후기 조회에 성공")
            }
    )
    @GetMapping("/grade/user/{id}")
    public ResponseEntity<List<GradeResponseDto>> getGradeByUserId(@PathVariable("id") Integer id) {
        List<GradeResponseDto> grades = gradeService.getGradeByUserId(Long.valueOf(id));
        return ResponseEntity.ok(grades);
    }

    @Operation(summary = "후기 정보 생성 API", description = "후기 정보를 받아 새로운 예약을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 후기 생성에 성공")
            }
    )
    @PostMapping("/grade")
    public ResponseEntity<GradeResponseDto> createGrade(@RequestBody GradeRequestDto requestDto) {
        log.info(requestDto.toString());
        GradeResponseDto responseDto = gradeService.createGrade(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "후기 정보 업데이트 API", description = "후기 정보를 받아 예약을 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "후기 정보 업데이트에 성공")
            }
    )
    @PatchMapping("/grade/{id}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Long id, @RequestBody GradeRequestDto requestDto) {
        log.info(requestDto.toString());
        GradeResponseDto responseDto = gradeService.updateGrade(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "후기 정보 삭제 API", description = "후기 아이디를 받아 후기을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "후기 삭제에 성공")
            }
    )
    @DeleteMapping("/grade/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
