package com.team.servicebooking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

@Configuration
public class GeminiConfig {

    @Value("${GEMINI_API_KEY:}")
    private String geminiApiKey;

    @Bean
    public Client geminiClient() {
        return Client.builder().apiKey(geminiApiKey).build();
    }

}
