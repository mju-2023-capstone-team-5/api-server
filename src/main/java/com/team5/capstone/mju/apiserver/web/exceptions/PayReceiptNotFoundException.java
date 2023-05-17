package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class PayReceiptNotFoundException extends CustomException {
    public PayReceiptNotFoundException(String paymentMethod) {
        super(paymentMethod + "결제 내역이 존재하지 않습니다. ");
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
