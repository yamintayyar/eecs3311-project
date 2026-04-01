package com.team.servicebooking.dto;

public class ChatResponseDTO {
    private String reply;
    public ChatResponseDTO(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
