package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "faq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id", nullable = false, columnDefinition = "int")
    private Long faqId;

    @Column(name = "question", columnDefinition = "TEXT")
    @Lob // Text 타입을 매칭할 때 사용, 뜻은: Large object
    private String question;

    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

}