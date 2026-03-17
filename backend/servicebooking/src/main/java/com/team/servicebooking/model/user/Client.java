package com.team.servicebooking.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private DatabaseSingleton database;

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

    //TODO: recreate paymentMethod addition/removal with databases
    void addPaymentMethod(PaymentMethodStrategy paymentMethod) {
        paymentMethods.add(paymentMethod);
    }

    void removePaymentMethod(PaymentMethodStrategy paymentMethod) {
        paymentMethods.remove(paymentMethod);
    }

    List<Booking> getBookingHistory() {
        List<Booking> t = new ArrayList<Booking>();
        t.addAll(bookings); // deep copy of list, shallow copy of all bookings within the list
        return t;
    }

    List<Payment> getPaymentHistory() {
        List<Payment> t = new ArrayList<Payment>();
        t.addAll(payments); // deep copy of list, shallow copy of all payments within the list
        return t;
    }

    void cancelBooking(Booking booking) {
        for (Booking b : bookings) {
            if (b.getID() == booking.getID()) {
                b.cancel();
            }
        }
    }

    void requestBooking(Consultant consultant, List<Availability> availability, Service service) {
        Booking b = new Booking(this, consultant, service, availability);

        bookings.add(b);

        consultant.notify(String.format("Booking %s has been requested by user %s", b.getID().toString(), this.name)); //TODO: use standardized notification method instead, if that is our new approach
    }

    void processPayment(Booking booking, PaymentMethodStrategy paymentMethod) throws InterruptedException {

        Payment p = Payment.processPayment(booking, paymentMethod);
        if (p != null)
            payments.add(p);

    }

    List<Service> browseServices() {

        List<Consultant> consultantList = database.getConsultants();

        List<Service> serviceList = new ArrayList<>(); // go through list of consultants, gather all services
        for (Consultant c : consultantList) {
            serviceList.addAll(c.getServices());
        }

        return serviceList;

    }

}
