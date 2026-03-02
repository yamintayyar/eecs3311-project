package servicebooking.src.main.java.com.team.servicebooking.model.user;

import servicebooking.src.main.java.com.team.servicebooking.config.DatabaseSingleton;
import servicebooking.src.main.java.com.team.servicebooking.model.availability.Availability;
import servicebooking.src.main.java.com.team.servicebooking.model.booking.Booking;
import servicebooking.src.main.java.com.team.servicebooking.model.payment.Payment;
import servicebooking.src.main.java.com.team.servicebooking.model.payment.PaymentMethodStrategy;
import servicebooking.src.main.java.com.team.servicebooking.model.service.Service;

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

    List<Booking> getBookingHistory(){
        List<Booking> t = new ArrayList<Booking>();
        t.addAll(bookings); //deep copy of list, shallow copy of all bookings within the list
        return t;
    }

    List<Payment> getPaymentHistory() {
        List<Payment> t = new ArrayList<Payment>();
        t.addAll(payments); //deep copy of list, shallow copy of all payments within the list
        return t;
    }

    void cancelBooking(Booking booking) {
        for (Booking b : bookings) {
            if (b.getID() == booking.getID()) {
                b.cancel();
            }
        }
    }

    void requestBooking(Consultant consultant, Availability availability, Service service) {
        Booking b = new Booking(this, consultant, service, availability);

        bookings.add(b);

        consultant.notify( String.format("Booking %s has been requested by user %s", b.getID().toString(), this.name) ); //notify consultant of new booking
    }

    void processPayment(Booking booking, PaymentMethodStrategy paymentMethod) {

        //check if payment method is valid, and then ask booking class if it can be paid (if status is confirmed)
        //finally, create payment and add it to payment history

        if (! paymentMethod.validate()){
            System.out.println("Error: Invalid payment method");
            return;
        }

        if (!booking.payable()) {
            System.out.println("Error: servicebooking.src.main.java.com.team.servicebooking.model.booking.Booking has not been confirmed yet by consultant. Please await their response.");
            return;
        }

        Payment p = new Payment(booking, paymentMethod);
        payments.add(p);

    }

    List<Service> browseServices() {

        List<Consultant> consultantList = database.getConsultants();

        List<Service> serviceList = new ArrayList<>(); //go through list of consultants, gather all services
        for (Consultant c : consultantList) {
            serviceList.addAll(c.getServices());
        }

        return serviceList;

    }
   
}
