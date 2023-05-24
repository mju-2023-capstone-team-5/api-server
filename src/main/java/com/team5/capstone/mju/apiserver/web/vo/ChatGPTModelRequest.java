package com.team5.capstone.mju.apiserver.web.vo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatGPTModelRequest implements Serializable {
    private String model;
    private ChatGptMessage[] messages;
}
