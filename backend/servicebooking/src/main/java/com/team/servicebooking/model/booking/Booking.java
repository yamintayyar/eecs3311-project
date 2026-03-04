package servicebooking.src.main.java.com.team.servicebooking.model.booking;

import servicebooking.src.main.java.com.team.servicebooking.config.DatabaseSingleton;
import servicebooking.src.main.java.com.team.servicebooking.model.availability.Availability;
import servicebooking.src.main.java.com.team.servicebooking.model.payment.Payment;
import servicebooking.src.main.java.com.team.servicebooking.model.service.Service;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Client;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Consultant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class Booking {
    private UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private List<Availability> availability;
    private LocalDate bookingDate;
    private BookingState bookingState;
    private Payment payment;

    private DatabaseSingleton database = DatabaseSingleton.getInstance();


    public Booking(Client client, Consultant consultant, Service service, List<Availability> availabilty) {
    	this.booking_id = UUID.randomUUID();
    	this.client = client;
    	this.consultant = consultant;
    	this.service = service;
    	this.availability = availabilty;
    	this.bookingDate = LocalDate.now();
    	this.bookingState = new RequestState(this);

    	System.out.println("Booking requested for " + client.getName() + "on " + bookingDate.toString() + ".");   //TODO: test to make sure that formatting is correct

        if (database.getVerboseNotification()) {
            client.notify("Booking created for " + bookingDate.toString() + ".");
        }

    }

    public java.util.UUID getID() {
        return booking_id;
    }

    public void changeState(BookingState bookingState) {
    	//Changed return type from boolean to void.
    	this.bookingState = bookingState;
    }


    public boolean payable() {
        return this.bookingState.payable();
    }

    public void cancel() {

        int min_notice = database.getMinNotice();

        LocalDateTime cancel_deadline = availability.get(0).getStartTime().plusHours(-min_notice); //get start time of beginning of session

        if (cancel_deadline.compareTo( LocalDateTime.now() ) < 0 ) {
            System.out.println("Error: Can not cancel booking because deadline has passed.");

            if (database.getVerboseNotification()) {
                client.notify("Error: Can not cancel booking because deadline has passed.");
            }

            return;
        }

        if (bookingState.isRefundable() && database.getRefundPolicy()) { //if booking is in a refundable state (paid) and the application has a refund policy active
            payment.markRefunded();
            System.out.println("Successfully refunded payment for booking " + booking_id);

            if (database.getVerboseNotification()) {
                client.notify("Successfully refunded payment for booking " + booking_id);
            }
        }

    	this.bookingState.cancel();
    }

    public void reject() {
    	this.bookingState.reject();

        client.notify("Booking " + booking_id + " has been rejected by consultant " + consultant.getName());
    }

    public double getPrice(){
        return service.getPrice();
    }

    public void pay(Payment payment)
    {
        this.payment = payment;

        if (database.getVerboseNotification()) {
            String notification = "Booking " + booking_id + " has been paid for";
            client.notify(notification);
            consultant.notify(notification);
        }
    }

    public boolean paid(){
        return payment!=null; //returns true if payment has been set, and thus pay() was called
    }

    public void confirm() {
        this.bookingState.confirm();
    }

    public void complete() {
        this.bookingState.complete();
    }
}
