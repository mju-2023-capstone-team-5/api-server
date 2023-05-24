package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatGptMessage implements Serializable {
    private String role;
    private String content;
}
