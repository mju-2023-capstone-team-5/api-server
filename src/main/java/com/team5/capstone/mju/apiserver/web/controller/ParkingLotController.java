package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestOldDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseOldDto;
import com.team5.capstone.mju.apiserver.web.service.AmazonS3Service;
import com.team5.capstone.mju.apiserver.web.service.ParkingLotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController  // 컨트롤러 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Controller 클래스는 스프링이 Bean으로 관리
// RESTController ==> @Controller + @RestponseBody를 묶은 것으로,
// 이 컨트롤러 내에서 반환하는 값은 기본적으로 String 또는 json 구조의 String ==> 이 클래스 내에서는 RESTful한 API만 개발하겠다는 뜻
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
@Tag(name = "주차장 Controller", description = "주차장 관련 API 요청 Controller")
// 현재까지의 API 요청 url: localhost:8080 + /api/v1
public class ParkingLotController {

    //서비스 객체
    private final ParkingLotService parkingLotService;
    private final AmazonS3Service amazonS3Service;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotController(ParkingLotService parkingLotService, AmazonS3Service amazonS3Service) {
        this.parkingLotService = parkingLotService;
        this.amazonS3Service = amazonS3Service;
    }

    @Operation(summary = "주차장 정보 반환 API", description = "주차장의 아이디를 받아 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 정보 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "주차장 정보나 주차장 주인에 대한 정보가 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/parking-lots/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<ParkingLotResponseDto> getParkingLot(@PathVariable("id") Long id) { // url path에 있는 {id}와 변수를 매핑
        // 서비스를 호출하여 얻어온 결과값을 반환. 반환 시 json 구조의 String으로 스프링이 해석하여 API 요청에 반환해줌
        return ResponseEntity.ok(parkingLotService.getParkingLotInfo(id));
    }

    @Operation(summary = "주차장 정보 반환 API", description = "왼쪽 위, 오른쪽 아래 사각형의 범위를 받아 주차장들의 정보를 반환하는 API",
            parameters = {
                    @Parameter(name = "x1", description = "왼쪽 위 위도"),
                    @Parameter(name = "y1", description = "왼쪽 위 경도"),
                    @Parameter(name = "x2", description = "오른쪽 아래 위도"),
                    @Parameter(name = "y2", description = "오른쪽 아래 경도")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 정보 조회에 성공")
            }
    )
    @GetMapping("/parking-lots/rectangle")
    public ResponseEntity<List<ParkingLotResponseDto>> getParkingLotsInDisplay(
            @RequestParam BigDecimal x1, // 왼쪽 위 위도
            @RequestParam BigDecimal y1, // 왼쪽 위 경도
            @RequestParam BigDecimal x2, // 오른쪽 아래 위도
            @RequestParam BigDecimal y2 // 오른쪽 아래 경도
    ) {
        return ResponseEntity.ok(parkingLotService.getParkingLotsInRectangle(x1, y1, x2, y2));
    }

    @Operation(summary = "주차장 정보 생성 API", description = "주차장의 정보를 받아 새로운 주차장을 생성하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "새로운 주차장 생성에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자나 주차장 주인에 대한 정보가 존재하지 않아 생성에 실패")
            }
    )
    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLotResponseDto> createParkingLot(@RequestBody ParkingLotDto requestDto) {
        log.info("POST /api/v1/parking-lots | DTO: " + requestDto.toString());
        ParkingLotResponseDto responseDto = parkingLotService.createParkingLot(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 주차장 정보 사진 업로드 url
    @Operation(summary = "주차장 정보 사진 업로드 API", description = "주차장의 사진을 받아 서버에 업로드하고 주차장 정보를 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장의 사진 업로드 및 url 업데이트에 성공"),
                    @ApiResponse(responseCode = "400", description = "이미지 파일에 문제가 있거나 허용되지 않은 이미지 확장자를 사용하여 업로드 및 url 업데이트에 실패"),
                    @ApiResponse(responseCode = "404", description = "주차장 정보가 존재하지 않아 업로드 및 url 업데이트에 실패")
            }
    )
    @PostMapping(value = "/parking-lots/{id}/images/info", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadParkingLotInfoImage(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile file) {
        ParkingLotResponseDto parkingLotInfo = parkingLotService.getParkingLotInfo(id);
        String url = amazonS3Service.uploadImage("parking-lots/" + id + "/", "info", file);
        parkingLotService.addImageUrl(id, url);
        return ResponseEntity.ok("upload success");
    }

    // 주차장 허가 문서 업로드 url, 관리자에 의해 수동으로 검사되어 허가 처리되는 API
    @Operation(summary = "주차장 허가(수동) 문서 사진 업로드 API", description = "주차장의 허가 문서 사진을 받아 서버에 업로드하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 허가 문서의 사진 업로드에 성공"),
                    @ApiResponse(responseCode = "400", description = "이미지 파일에 문제가 있거나 허용되지 않은 이미지 확장자를 사용하여 허가 문서의 사진 업로드에 실패"),
                    @ApiResponse(responseCode = "404", description = "주차장 정보가 존재하지 않아 허가 문서의 사진 업로드에 실패")
            }
    )
    @PostMapping(value = "/parking-lots/{id}/images/permit-request", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadParkingLotPermitRequestImage(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        ParkingLotResponseDto parkingLotInfo = parkingLotService.getParkingLotInfo(id);
        amazonS3Service.uploadImages("parking-lots/" + id + "/permit-req/", "request", files);
        return ResponseEntity.ok("upload success");
    }

    // 주차장 허가 문서 업로드 API, OpenAPI에 의해 자동으로 검사되어 허가 처리되는 API
    @Operation(hidden = true, summary = "주차장 허가(자동) 문서 사진 업로드 API", description = "주차장의 허가 문서 사진을 받아 서버에 업로드 하고 OpenAPI에 의해 자동으로 검사되어 허가 처리되는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 허가 문서의 사진 업로드에 성공 및 허가 처리 완료 됨")
            }
    )
    @PostMapping(value = "/parking-lots/{id}/images/permit-request-auto", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadParkingLotPermitRequestImageAuto(@PathVariable Long id, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        return ResponseEntity.ok("upload success");
    }

    @Operation(summary = "주차장 정보 업데이트 API", description = "주차장의 정보를 받아 정보를 업데이트 하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 정보 업데이트에 성공"),
                    @ApiResponse(responseCode = "404", description = "주차장이나 주차장 주인에 대한 정보가 존재하지 않아 정보 업데이트에 실패"),
            }
    )
    @PatchMapping("/parking-lots/{id}")
    public ResponseEntity<ParkingLotDto> updateParkingLot(@PathVariable Long id, @RequestBody ParkingLotDto requestDto) {
        log.info(requestDto.toString());
        ParkingLotDto responseDto = parkingLotService.updateParkingLot(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "주차장 정보 삭제 API", description = "주차장의 아이디를 받아 주차장을 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 삭제에 성공"),
                    @ApiResponse(responseCode = "400", description = "주차장 정보가 존재하지 않아 삭제에 실패"),
            }
    )
    @DeleteMapping("/parking-lots/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }
}
