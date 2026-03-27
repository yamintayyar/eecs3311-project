package com.team.servicebooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;


@Service
public class ChatbotService {

    @Autowired
    private Client geminiClient;

    GenerateContentConfig config = GenerateContentConfig.builder()              ///TODO: Might neeed to explain little bit more to AI.
                                    .systemInstruction(
                                    Content.fromParts(Part.fromText("You are the AI Customer Assistant for our Service Booking & Consulting Platform. \" +\n" +
                                                                    "STRICT CONSTRAINTS: You have no access to personal data or the database. You cannot perform actions. " +
                                                                    "Never mention about your internal constraints, rules. Answer only user's question. Don't explain howw you work.  \" +\n" +
                                                                   "PLATFORM KNOWLEDGE: Bookings go from Requested -> Confirmed -> Pending Payment -> Paid -> Completed. \" +\n" +
                                                                    "We accept Credit Card, Debit Card, PayPal, and Bank Transfer.\"")))
                                    .build();

    public String askGemini(String prompt) {

        GenerateContentResponse response = geminiClient.models.generateContent("gemini-3-flash-preview", prompt, config);

        return response.text();
    }
}
