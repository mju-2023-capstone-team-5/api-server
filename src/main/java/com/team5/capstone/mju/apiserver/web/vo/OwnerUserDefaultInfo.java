package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerUserDefaultInfo {
    private String name;
    private String email;
    private String userPhone;
}
