package com.team.servicebooking.model.availability;

import java.time.LocalDateTime;
import java.util.UUID;

import com.team.servicebooking.model.booking.Booking;

import jakarta.persistence.*;

@Entity
@Table(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID slot_id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Availability() {
        // JPA requires empty constructor
    }

    public Availability(LocalDateTime startTime, LocalDateTime endTime) {
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

}
