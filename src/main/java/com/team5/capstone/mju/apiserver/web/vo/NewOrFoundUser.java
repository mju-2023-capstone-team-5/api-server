package com.team5.capstone.mju.apiserver.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewOrFoundUser {
    private Long userId;
    private boolean isNewUser;
}
