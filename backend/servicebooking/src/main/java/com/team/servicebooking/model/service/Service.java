package com.team.servicebooking.model.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.model.user.Consultant;

import jakarta.persistence.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID service_id;

    private String serviceName;
    private String service_description;
    private double price;
    private int duration; // slot quantity?

    @Transient
    private DatabaseSingleton database;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    public Service() {
        // JPA requires empty constructor
    }

    public Service(String serviceName, String service_description, double price, int duration) {
        this.serviceName = serviceName;
        this.service_description = service_description;
        this.price = price;
        this.duration = duration;
    }

    public UUID getServiceId() {
        return service_id;
    }

    public double getPrice() {
        return this.price * this.duration * database.applyDiscount(); // applies discounts if any are registered by
                                                                      // admin
    }

    public double getDuration() {
        return this.duration;
    }

    public String getName() {
        return this.serviceName;
    }

    public String getDescription() {
        return this.service_description;
    }

}
