package com.team5.capstone.mju.apiserver.web.controller;

import com.team5.capstone.mju.apiserver.web.exceptions.CustomException;
import io.sentry.Sentry;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Profile("server")
@ControllerAdvice
public class ServerGlobalExceptionController {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> handleUserNotFound(CustomException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotWritableException.class)
    protected ResponseEntity<?> handleJsonWriteParseError(org.springframework.http.converter.HttpMessageNotWritableException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("응답 JSON 데이터 생성에 문제가 있습니다. 서버에 문의하세요.");
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    protected ResponseEntity<?> handleJsonReadParseError(org.springframework.http.converter.HttpMessageNotReadableException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("요청 JSON 데이터 파싱에 문제가 있습니다. API 스펙을 확인해주세요.");
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<?> handleGlobalNullPointerException(NullPointerException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
