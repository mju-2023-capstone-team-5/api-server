package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.AnnouncementRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.AnnouncementResponseDto;
import com.team5.capstone.mju.apiserver.web.dto.GradeRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.GradeResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.Announcement;
import com.team5.capstone.mju.apiserver.web.entity.Grade;
import com.team5.capstone.mju.apiserver.web.exceptions.AnnouncementNotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.GradeNotFoundException;
import com.team5.capstone.mju.apiserver.web.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class AnnouncementService {

    // Repository 객체
    private final AnnouncementRepository announcementRepository;

    @Autowired // 생성자를 통한 의존성 주입
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Transactional
    public AnnouncementResponseDto getAnnouncementInfo(Long id) {
        Announcement found = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(id));

        AnnouncementResponseDto dto = AnnouncementResponseDto.builder()
                .announcementId(Math.toIntExact(found.getAnnouncementId()))
                .title(found.getTitle())
                .content(found.getContent())
                .timestamp(found.getTimestamp())
                .build();
        return dto;
    }

    // 공지사항 생성
    @Transactional
    public AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto requestDto) {

        // History 엔티티를 데이터베이스에 저장
        Announcement savedAnnouncement = announcementRepository.save(requestDto.ToEntity());

        AnnouncementResponseDto responseDto = new AnnouncementResponseDto();
        responseDto.setAnnouncementId(Math.toIntExact(savedAnnouncement.getAnnouncementId()));
        responseDto.setTitle(savedAnnouncement.getTitle());
        responseDto.setContent(savedAnnouncement.getContent());
        responseDto.setTimestamp(savedAnnouncement.getTimestamp());
        return responseDto;
    }

    // 공지사항 업데이트
    @Transactional
    public AnnouncementResponseDto updateAnnouncement(Long id, AnnouncementRequestDto requestDto) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(id));

        // 후기 상세 정보를 업데이트합니다.
        announcement.updateAllInfoSelf(requestDto);
        return AnnouncementResponseDto.of(announcement);
    }

    //공지사항 삭제
    @Transactional
    public void deleteAnnouncement(Long id) {
        Announcement found = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(id));
        announcementRepository.delete(found);
    }
}
