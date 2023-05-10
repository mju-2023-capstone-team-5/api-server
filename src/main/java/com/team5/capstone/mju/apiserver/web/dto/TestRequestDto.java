package com.team5.capstone.mju.apiserver.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TestRequestDto {

    @JsonProperty(value = "hello")
    public String test;

    @JsonProperty(value = "arr")
    public List<String> array;

    @JsonProperty(value = "history")
    public HistoryRequestDto historyRequestDto;

    @JsonProperty(value = "login")
    public LoginRequestDto loginRequestDto;

}
