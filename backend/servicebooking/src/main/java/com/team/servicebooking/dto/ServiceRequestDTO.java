package com.team.servicebooking.dto;

public class ServiceRequestDTO {
    private double price;
    private String name;
    private String description;
    private int durationHours;
    private String consultantId;


    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public int getDurationHours() {return durationHours;}

    public void setDurationHours(int durationHours) {this.durationHours = durationHours;}

    public String getConsultantId() {return consultantId;}

    public void setConsultantId(String consultantId) {this.consultantId = consultantId;}


}

