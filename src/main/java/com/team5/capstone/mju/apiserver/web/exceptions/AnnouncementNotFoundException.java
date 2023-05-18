package com.team5.capstone.mju.apiserver.web.exceptions;

import org.apache.http.HttpStatus;

public class AnnouncementNotFoundException extends CustomException {
    public AnnouncementNotFoundException(Integer id) {
        super("공지사항을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

    public AnnouncementNotFoundException(Long id) {
        super("공지사항을 찾을 수 없습니다. id: " + id);
        this.httpStatus = HttpStatus.SC_NOT_FOUND;
    }

}
