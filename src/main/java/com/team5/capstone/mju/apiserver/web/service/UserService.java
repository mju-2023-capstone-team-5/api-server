package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자를 삭제하는 메소드
    @Transactional
    public void deleteUser(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
        userRepository.delete(found);
    }

    // 사용자를 삭제하는 메소드
    // 카카오 계정 관리 페이지나 고객센터에서 연결끊기를 진행 시 넘어오는 정보는 kakaoId이기 때문에 kakaoId를 이용한 삭제처리
    @Transactional
    public void deleteUserFromKakaoId(Long kakaoId) {
        User found = userRepository.findByKakaoAppUuid(String.valueOf(kakaoId))
                .orElseThrow(() -> new EntityNotFoundException("해당하는 카카오 유저가 없습니다."));
        userRepository.delete(found);
    }
}
