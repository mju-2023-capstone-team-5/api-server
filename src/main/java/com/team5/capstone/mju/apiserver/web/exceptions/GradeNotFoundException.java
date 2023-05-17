package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class GradeNotFoundException extends CustomException {
    public GradeNotFoundException(Integer id) {
        super("후기를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public GradeNotFoundException(Long id) {
        super("후기를 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

}
