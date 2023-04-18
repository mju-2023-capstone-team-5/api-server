package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.ParkingLotRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotResponseDto;
import com.team5.capstone.mju.apiserver.web.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController  // 컨트롤러 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Controller 클래스는 스프링이 Bean으로 관리
// RESTController ==> @Controller + @RestponseBody를 묶은 것으로,
// 이 컨트롤러 내에서 반환하는 값은 기본적으로 String 또는 json 구조의 String ==> 이 클래스 내에서는 RESTful한 API만 개발하겠다는 뜻
@RequestMapping("/api/v1") // API 요청 URL의 앞에 오는 Prefix 설정
// 현재까지의 API 요청 url: localhost:8080 + /api/v1
public class ParkingLotController {

    //서비스 객체
    private final ParkingLotService parkingLotService;

    @Autowired // 생성자를 통한 의존성 주입
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("/parking-lots/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<ParkingLotResponseDto> getParkingLot(@PathVariable("id") Long id) { // url path에 있는 {id}와 변수를 매핑
        // 서비스를 호출하여 얻어온 결과값을 반환. 반환 시 json 구조의 String으로 스프링이 해석하여 API 요청에 반환해줌
        return ResponseEntity.ok(parkingLotService.getParkingLotInfo(id));
    }

    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLotResponseDto> createParkingLot(@RequestBody ParkingLotRequestDto requestDto) {
        log.info(requestDto.toString());
        ParkingLotResponseDto responseDto = parkingLotService.createParkingLot(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "/parking-lots/{id}/upload/images", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadParkingLotImage(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile[] files) {
        try {
            String fileName = StringUtils.cleanPath(files[0].getOriginalFilename());
            String uploadDir = "/Users/jaehan1346/Desktop/"; // 저장할 경로, 바로 아랫줄에서 fileName 앞에 /가 안 붙으므로 경로 마지막에 /를 붙였음
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            File dest = path.toFile();
            files[0].transferTo(dest);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/parking-lots/{id}")
    public ResponseEntity<ParkingLotResponseDto> updateParkingLot(@PathVariable Long id, @RequestBody ParkingLotRequestDto requestDto) {
        log.info(requestDto.toString());
        ParkingLotResponseDto responseDto = parkingLotService.updateParkingLot(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/parking-lots/{id}")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable Long id) {
        parkingLotService.deleteParkingLot(id);
        return ResponseEntity.noContent().build();
    }
}
