package com.example.wafi.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class WhatsAppService {

    @Value("${greenapi.id-instance}")
    private String idInstance;

    @Value("${greenapi.api-token}")
    private String apiToken;

    private final WebClient webClient = WebClient.create("https://api.green-api.com");

    public void sendWhatsApp(String phoneNumber, String text) {
        String url = "/waInstance" + idInstance + "/sendMessage/" + apiToken;

        webClient.post()
                .uri(url)
                .bodyValue(Map.of(
                        "chatId", phoneNumber + "@c.us",
                        "message", text
                ))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}