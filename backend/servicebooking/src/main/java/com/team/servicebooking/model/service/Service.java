package com.team.servicebooking.model.service;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team.servicebooking.model.user.Consultant;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private double price;
    private String description;
    private int durationHours;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    @JsonBackReference
    private Consultant consultant;

    protected Service() {
    }

    public Service(String name, double price, String description, Consultant consultant) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.consultant = consultant;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return durationHours;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public String getConsultantName() {
        return consultant != null ? consultant.getName() : null;
    }

    public UUID getConsultantId() {
        return consultant != null ? consultant.getUser_id() : null;
    }
}