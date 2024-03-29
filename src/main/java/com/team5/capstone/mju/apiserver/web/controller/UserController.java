package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.*;
import com.team5.capstone.mju.apiserver.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "유저 컨트롤러", description = "유저 관련 API 요청 controller")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "사용자 fcm token 정보 추가 API", description = "사용자의 fcm 토큰을 받아 사용자의 fcm 토큰 정보를 추가하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 fcm token 추가에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자의 정보가 존재하지 않아 추가에 실패")
            }
    )
    @PostMapping("/users/{id}/add-fcm-token") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<String> addFcmToken(@PathVariable Long id, @RequestBody UserAddFcmTokenRequestDTO requestDTO) {
        log.info("token: " + requestDTO.getFcmToken());
        return ResponseEntity.ok(userService.addFcmToken(id, requestDTO));
    }


    @Operation(summary = "사용자 정보 반환 API", description = "사용자의 아이디를 받아 사용자의 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자의 정보가 존재하지 않아 정보 조회에 실패")
            }
    )
    @GetMapping("/users/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @Operation(summary = "사용자가 등록한 주차장 정보 반환 API", description = "사용자의 아이디를 받아 등록한 주차장 정보 리스트를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 정보 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자의 정보가 존재하지 않아 주차장 정보 조회에 실패")
            }
    )
    @GetMapping("/users/{id}/parking-lots") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<List<ParkingLotResponseDto>> getAllParkingLotBiaOwner(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getMyAllParkingLots(id));
    }

    @Operation(summary = "사용자가 예약했던 예약 내역 반환 API", description = "사용자의 아이디를 받아 예약했던 주차장의 예약 내역을 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "예약 내역 정보 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자의 정보가 존재하지 않아 예약 내역 조회에 실패")
            }
    )
    @GetMapping("/users/{id}/parking-lots/history")
    // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<List<HistoryResponseDto>> getAllParkingLotReservationHistory(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAllMyHistory(id));
    }


    @Operation(summary = "주차장 주인 정보 반환 API", description = "owner의 아이디를 받아 주차장 주인의 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "owner 조회에 성공"),
                    @ApiResponse(responseCode = "404", description = "주차장 주인의 정보가 존재하지 않거나 주인의 사용자 정보가 존재하지 않아 조회에 실패")
            }
    )
    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerResponseDto> getOwner(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getParkingLotOwnerInfo(id));
    }

    @Operation(summary = "사용자 회원탈퇴 후처리 API", description = "사용자의 카카오 회원탈퇴 후 사용자의 정보를 서비스 서버에서 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 삭제에 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자의 정보가 존재하지 않아 삭제에 실패")
            }
    )
    @DeleteMapping("/users/leave")
    public void deleteUser(@RequestParam("user_id") Long userId) {
        userService.deleteUser(userId);
    }

    @Operation(hidden = true, summary = "사용자 회원탈퇴 후처리 API", description = "앱이 아닌 카카오 계정 관리 페이지나 고객센터에서 회원탈퇴 후 사용자의 정보를 서비스 서버에서 삭제하는 API, Kakao Developers에서 설정된 redirect에서 GET/POST 메소드만 제공하기 때문에 Http Method를 GET로 설정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 삭제에 성공"),
                    @ApiResponse(responseCode = "404", description = "카카오 사용자의 uuid가 존재하지 않아 삭제에 실패")
            }
    )
    @GetMapping("/users/leave-kakao") // Kakao Developers에서 설정된 redirect에서 GET/POST 메소드만 제공하기 때문에 Http Method를 GET로 설정
    public void deleteUserFromKakaoRedirect(@RequestParam("user_id") Long kakaoId) {
        userService.deleteUserFromKakaoId(kakaoId);
    }

}
