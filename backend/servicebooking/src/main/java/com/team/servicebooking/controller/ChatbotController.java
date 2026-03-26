package com.team.servicebooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.team.servicebooking.dto.ChatRequestDTO;
import com.team.servicebooking.dto.ChatResponseDTO;
import com.team.servicebooking.service.ChatbotService;

@RestController
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/api/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        
        String response = chatbotService.askGemini(request.getMessage());

        return ResponseEntity.ok(new ChatResponseDTO(response));
    }
}
