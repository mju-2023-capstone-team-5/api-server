package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class QnANotFoundException extends CustomException {
    public QnANotFoundException(Integer id) {
        super("QnA를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public QnANotFoundException(Long id) {
        super("QnA를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }
}
