package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.QnaRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.QnaResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.Qna;
import com.team5.capstone.mju.apiserver.web.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class CustomerSupportService {

    // Repository 객체
    private final QnaRepository qnaRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public CustomerSupportService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @Transactional(readOnly = true)
    public QnaResponseDto getQnaInfo(Long id) {
        Qna found = qnaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QnA를 찾을 수 없습니다."));

        return QnaResponseDto.of(found);
    }

    // QnA생성
    @Transactional
    public QnaResponseDto createQna(QnaRequestDto requestDto) {
        // QnA 엔티티를 데이터베이스에 저장
        Qna savedQna = qnaRepository.save(requestDto.toEntity());
        return QnaResponseDto.of(savedQna);
    }

    // QnA 업데이트
    @Transactional
    public QnaResponseDto updateQna(Long id, QnaRequestDto requestDto) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QnA를 찾을 수 없습니다."));

        // 예약 상세 정보를 업데이트합니다.
        qna.updateAllInfoSelf(requestDto);
        return QnaResponseDto.of(qna);
    }

    // 예약정보 삭제
    @Transactional
    public void deleteQna(Long id) {
        qnaRepository.deleteById(id);
    }
}
