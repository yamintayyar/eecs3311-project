package com.team.servicebooking.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.service.Service;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultants")
public class Consultant extends User {

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Availability> availabilities = new ArrayList<>();

    protected Consultant() {
    }

    public Consultant(String name, String email, String password) {
        super(name, email, password);
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void addAvailability(Availability availability) {
        availability.setConsultant(this);
        availabilities.add(availability);
    }

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        service.setConsultant(this);
        services.add(service);
    }

    public void clearServices() {
        services.clear();
    }
}