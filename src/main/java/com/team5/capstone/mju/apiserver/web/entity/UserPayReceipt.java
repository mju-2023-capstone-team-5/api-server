package com.team5.capstone.mju.apiserver.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_pay_receipt")
public class UserPayReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pay_receipt_id", nullable = false, columnDefinition = "int")
    private Long userPayReceiptId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "amount")
    private Long amount;

    @Lob
    @Column(name = "payment_type", columnDefinition = "TEXT")
    private String paymentType;

}