package com.team5.capstone.mju.apiserver.web.service;

import com.team5.capstone.mju.apiserver.web.dto.FaQDto;
import com.team5.capstone.mju.apiserver.web.dto.QnaRequestDto;
import com.team5.capstone.mju.apiserver.web.dto.QnaResponseDto;
import com.team5.capstone.mju.apiserver.web.entity.Faq;
import com.team5.capstone.mju.apiserver.web.entity.Qna;
import com.team5.capstone.mju.apiserver.web.entity.User;
import com.team5.capstone.mju.apiserver.web.exceptions.QnANotFoundException;
import com.team5.capstone.mju.apiserver.web.exceptions.UserNotFoundException;
import com.team5.capstone.mju.apiserver.web.repository.FaqRepository;
import com.team5.capstone.mju.apiserver.web.repository.QnaRepository;
import com.team5.capstone.mju.apiserver.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service // 서비스 레이어임을 알리는 어노테이션. 이 어노테이션을 붙이면 Service 클래스는 스프링이 Bean으로 관리
public class CustomerSupportService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    // Repository 객체
    private final QnaRepository qnaRepository;
    private final FaqRepository faqRepository;


    @Autowired // 생성자를 통한 의존성 주입
    public CustomerSupportService(QnaRepository qnaRepository, JavaMailSender mailSender, UserRepository userRepository, FaqRepository faqRepository) {
        this.qnaRepository = qnaRepository;
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.faqRepository = faqRepository;
    }

    @Transactional(readOnly = true)
    public List<QnaResponseDto> getQnaInfo(Long id) {
        ArrayList<Qna> foundList = qnaRepository.findAllByUserId(Math.toIntExact(id));

        ArrayList<QnaResponseDto> responseDtos = new ArrayList<>();

        foundList.forEach((qna -> {
            responseDtos.add(QnaResponseDto.of(qna));
        }));

        return responseDtos;
//
//        Qna found = qnaRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("QnA를 찾을 수 없습니다."));
//
//        return QnaResponseDto.of(found);
    }

    // QnA생성
    @Transactional
    public QnaResponseDto createQna(QnaRequestDto requestDto) {
        // 이메일 보내기
        User user = userRepository.findById((long) requestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));

        // QnA 엔티티를 데이터베이스에 저장
        Qna savedQna = qnaRepository.save(requestDto.toEntity());

        String userEmail = user.getEmail();
        String emailSubject = "QnA 등록 안내";
        String emailContent = "성공적으로 QnA를 등록하였습니다.";
        sendEmail(userEmail, emailSubject, emailContent);

        return QnaResponseDto.of(savedQna);
    }



    public void sendEmail(String userEmail, String emailSubject, String emailContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(emailSubject);
        message.setText(emailContent);
        mailSender.send(message);
    }

    // QnA 업데이트
    @Transactional
    public QnaResponseDto updateQna(Long id, QnaRequestDto requestDto) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new QnANotFoundException(id));

        // QnA 상세 정보를 업데이트합니다.
        qna.updateAllInfoSelf(requestDto);
        return QnaResponseDto.of(qna);
    }

    // QnA정보 삭제
    @Transactional
    public void deleteQna(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new QnANotFoundException(id));

        qnaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<FaQDto> getAllFaQ() {
        List<Faq> all = faqRepository.findAll();
        List<FaQDto> faQDtos = all.stream().map(FaQDto::of).collect(Collectors.toList());
        return faQDtos;
    }
}
