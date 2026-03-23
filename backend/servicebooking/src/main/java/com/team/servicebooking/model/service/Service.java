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

    public Service(String name, double price, String description, Consultant consultant) { //can we pass an entire object, instead of just an ID?
        this.name = name;
        this.price = price;
        this.description = description;
        this.consultant = consultant;
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
}
