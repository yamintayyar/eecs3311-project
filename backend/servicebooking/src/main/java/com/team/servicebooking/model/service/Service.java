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

    private int durationHours; //TODO: implement; currently not set or accounted for by price

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    @JsonBackReference
    private Consultant consultant;

    protected Service() {
    }

    public Service(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

//    public double getDuration() {
//        return this.duration;
//    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
