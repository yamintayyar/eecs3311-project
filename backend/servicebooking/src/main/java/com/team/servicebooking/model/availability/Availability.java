package com.team.servicebooking.model.availability;

import java.time.LocalDateTime;
import java.util.UUID;

public class Availability {
    private UUID slot_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked = false;

    public Availability(LocalDateTime startTime, LocalDateTime endTime) {
        this.slot_id = UUID.randomUUID();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getSlotId() {
        return slot_id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void markBooked() {
        isBooked = true;
    }

    public void markAvailable() {
        isBooked = false;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

}
