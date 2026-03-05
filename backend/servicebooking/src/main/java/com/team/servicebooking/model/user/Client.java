package com.team.servicebooking.model.user;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.payment.Payment;
import com.team.servicebooking.model.payment.PaymentMethodStrategy;
import com.team.servicebooking.model.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client extends User {
    private List<PaymentMethodStrategy> paymentMethods;
    private List<Payment> payments;
    private List<Booking> bookings;
    private DatabaseSingleton database;

    public Client(UUID user_id, String name, String email, String password) {
        super(user_id, name, email, password);

        paymentMethods = new ArrayList<>();
        payments = new ArrayList<>();
        bookings = new ArrayList<>();
        database = DatabaseSingleton.getInstance();

    }

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

        consultant.notify(String.format("Booking %s has been requested by user %s", b.getID().toString(), this.name)); // notify
                                                                                                                       // consultant
                                                                                                                       // of
                                                                                                                       // new
                                                                                                                       // booking
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
