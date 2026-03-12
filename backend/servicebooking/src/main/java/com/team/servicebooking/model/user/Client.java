package com.team.servicebooking.model.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.service.Service;

@Entity
@Table(name = "clients")
public class Client extends User {

    /*
     * @OneToMany(mappedBy = "client")
     * 
     * @JsonManagedReference
     * private List<Booking> bookings = new ArrayList<>();
     */

    protected Client() {
    }

    public Client(String name, String email, String password) {
        super(name, email, password);
    }

    /*
     * public List<Booking> getBookings() {
     * return bookings;
     * }
     */
}