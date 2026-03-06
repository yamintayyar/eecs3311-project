package com.team.servicebooking.model.service;


import com.team.servicebooking.config.DatabaseSingleton;

import java.util.UUID;

public class Service {
    private UUID service_id;
    private String serviceName;
    private String service_description;
    private double price;
    private int duration;  //slot quantity?

    private DatabaseSingleton database = DatabaseSingleton.getInstance();
    
    public Service(String serviceName, String service_description, double price, int duration) {
    	this.service_id = UUID.randomUUID();
    	this.serviceName = serviceName;
    	this.service_description = service_description;
    	this.price = price;
    	this.duration = duration;
    }

    public double getPrice() {
        return this.price * this.duration * database.applyDiscount(); //applies discounts if any are registered by admin
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
