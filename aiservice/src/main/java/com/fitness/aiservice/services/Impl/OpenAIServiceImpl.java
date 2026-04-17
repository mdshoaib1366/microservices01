package com.fitness.aiservice.services.Impl;

import com.fitness.aiservice.services.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    private final WebClient webClient;

    @Override
    public String askToChatGpt(String question) {

        Map<String, Object> request = Map.of(
                "model", "gpt-4.1-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", question)
                )
        );

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .map(body -> {
                                    System.out.println("OpenAI Error Body: " + body);
                                    return new RuntimeException(body);
                                })
                )
                .bodyToMono(String.class)
                .block();
    }
}
