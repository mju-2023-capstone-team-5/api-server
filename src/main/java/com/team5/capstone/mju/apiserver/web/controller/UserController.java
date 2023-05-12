package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.dto.OwnerResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.ParkingLotDto;
import com.team5.capstone.mju.apiserver.web.dto.UserResponseDto;
import com.team5.capstone.mju.apiserver.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "유저 컨트롤러", description = "유저 관련 API 요청 controller")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "사용자 정보 반환 API", description = "사용자의 아이디를 받아 사용자의 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 조회에 성공")
            }
    )
    @GetMapping("/users/{id}") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @Operation(summary = "사용자가 등록한 주차장 정보 반환 API", description = "사용자의 아이디를 받아 등록한 주차장 정보 리스트를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주차장 정보 조회에 성공")
            }
    )
    @GetMapping("/users/{id}/parking-lots") // HTTP 메소드 별 URL 매핑. localhost:8080/api/v1/parking-lots/1이면 id 변수가 1
    public ResponseEntity<List<ParkingLotDto>> getAllParkingLotBiaOwner(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getMyAllParkingLots(id));
    }

    @Operation(summary = "주차장 주인 정보 반환 API", description = "owner의 아이디를 받아 주차장 주인의 정보를 반환하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "owner 조회에 성공")
            }
    )
    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerResponseDto> getOwner(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getParkingLotOwnerInfo(id));
    }

    @Operation(summary = "사용자 회원탈퇴 후처리 API", description = "사용자의 카카오 회원탈퇴 후 사용자의 정보를 서비스 서버에서 삭제하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 삭제에 성공")
            }
    )
    @DeleteMapping("/users/leave")
    public void deleteUser(@RequestParam("user_id") Long userId) {
        userService.deleteUser(userId);
    }

    @Operation(hidden = true, summary = "사용자 회원탈퇴 후처리 API", description = "앱이 아닌 카카오 계정 관리 페이지나 고객센터에서 회원탈퇴 후 사용자의 정보를 서비스 서버에서 삭제하는 API, Kakao Developers에서 설정된 redirect에서 GET/POST 메소드만 제공하기 때문에 Http Method를 GET로 설정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 정보 삭제에 성공")
            }
    )
    @GetMapping("/users/leave-kakao") // Kakao Developers에서 설정된 redirect에서 GET/POST 메소드만 제공하기 때문에 Http Method를 GET로 설정
    public void deleteUserFromKakaoRedirect(@RequestParam("user_id") Long kakaoId) {
        userService.deleteUserFromKakaoId(kakaoId);
    }

}
