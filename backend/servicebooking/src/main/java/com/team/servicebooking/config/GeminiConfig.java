package com.team.servicebooking.config;

import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

@Configuration
public class GeminiConfig {

    public Client geminiClient() {
        return new Client();
    }
    
}
