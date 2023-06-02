package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "declaration")
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaration_id", nullable = false, columnDefinition = "int")
    private Long declarationId;

    @Column(name = "declaration_date")
    private LocalDateTime declarationDate;

    @Column(name = "declaration_time")
    private LocalDateTime declarationTime;

    @Column(name = "declaration_detail")
    private String declarationDetail;

}