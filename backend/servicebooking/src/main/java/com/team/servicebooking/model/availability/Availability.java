package com.team.servicebooking.model.availability;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Consultant;

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
    @JoinColumn(name = "consultant_id")
    @JsonBackReference
    private Consultant consultant;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    public Availability() {
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

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}