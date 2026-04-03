package com.team.servicebooking.dto;

import java.time.LocalDateTime;

public class AvailabilityRequestDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;

    public AvailabilityRequestDTO() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}