package com.team5.capstone.mju.apiserver.web.dto;

import com.team5.capstone.mju.apiserver.web.entity.Faq;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaQDto {
    private String question;
    private String answer;

    public static FaQDto of(Faq faq) {
        return FaQDto.builder().question(faq.getQuestion()).answer(faq.getAnswer()).build();
    }
}
