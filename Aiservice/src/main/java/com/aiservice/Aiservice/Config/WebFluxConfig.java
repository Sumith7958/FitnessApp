package com.aiservice.Aiservice.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class WebFluxConfig {
    private final WebClient webClient;

    @Value("${app.geminiUri}")
    private String geminiUri;
    @Value("${app.geminiApi}")
    private String getGeminiApi;

    public WebFluxConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }


    public String getResponse(String details){
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", details)
                        })
                }
        );

        String response = webClient.post()
                .uri(geminiUri)
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", getGeminiApi) // Use the header for the key
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}