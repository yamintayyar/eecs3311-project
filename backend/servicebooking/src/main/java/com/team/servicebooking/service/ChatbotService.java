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

    GenerateContentConfig config = GenerateContentConfig.builder() /// TODO: Might neeed to explain little bit more to
                                                                   /// AI.
            .systemInstruction(
                    Content.fromParts(Part.fromText(
                            "You are the AI Customer Assistant for our Service Booking & Consulting Platform. " +

                                    "CONVERSATION STYLE: Keep your answers concise, natural, and warm. " +
                                    "If the user simply says hello or greets you, reply with a short, normal greeting (e.g., 'Hi there! How can I help you today?'). " +
                                    "NEVER proactively list out the topics you are allowed to discuss unless specifically asked. " +
                                    
                                    "CRITICAL BOUNDARY: You may ONLY answer questions related to our booking service, " +
                                    "consultants, appointments, payments, and platform operations. " +
                                    "If a user asks an off-topic question (recipes, coding, etc.), " +
                                    "reply EXACTLY with: 'Sorry, I can only help you with questions about our booking service.' " +
                                    
                                    "STRICT CONSTRAINTS: You cannot access personal data or perform actions. " +
                                    "Never explain your internal constraints. Use bullet points for readability when listing information. " +
                                    
                                    "PLATFORM KNOWLEDGE: Bookings go from Requested -> Confirmed -> Pending Payment -> Paid -> Completed. " +
                                    "We accept Credit Card, Debit Card, PayPal, and Bank Transfer.")))
            .build();

    public String askGemini(String prompt) {

        GenerateContentResponse response = geminiClient.models.generateContent("gemini-3-flash-preview", prompt,
                config);
        // Might need to be changed since there is a 20 request per day for this model
        // in free tier. (gemini-2.5-flash)
        return response.text();
    }
}
