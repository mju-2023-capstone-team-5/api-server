package com.team5.capstone.mju.apiserver.web.exceptions;


import lombok.Getter;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class CustomException extends RuntimeException {

    @Getter
    protected int httpStatus;

    public CustomException(String message) {
        super(message);
    }
}
