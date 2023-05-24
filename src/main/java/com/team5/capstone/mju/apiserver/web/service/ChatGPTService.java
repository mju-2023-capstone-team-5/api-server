package com.team5.capstone.mju.apiserver.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.capstone.mju.apiserver.web.vo.ChatGPTModelRequest;
import com.team5.capstone.mju.apiserver.web.vo.ChatGptMessage;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGPTService {

    @Value("${extends.openai.private-api-key}")
    private String apiKey;

    private final String model = "gpt-3.5-turbo-0301";
    private final String role = "user";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String sendChat(String content) throws JsonProcessingException {
        String url = "https://api.openai.com/v1/chat/completions";

        ChatGPTModelRequest requestEntity = ChatGPTModelRequest.builder()
                .model(model)
                .messages(
                        new ChatGptMessage[]{
                                ChatGptMessage.builder()
                                        .role(role)
                                        .content(content)
                                        .build()
                        }
                )
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
//        headers.add("Content-Type", "application/json");

        HttpEntity<ChatGPTModelRequest> httpEntity = new HttpEntity<>(requestEntity, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            JsonNode node = objectMapper.readTree(responseEntity.getBody());
            return node.get("choices").get(0).get("message").get("content").asText();
        }
        else {
            throw new RuntimeException();
        }
    }
}
