package servicebooking.src.main.java.com.team.servicebooking.model.booking;

import servicebooking.src.main.java.com.team.servicebooking.model.availability.Availability;
import servicebooking.src.main.java.com.team.servicebooking.model.payment.Payment;
import servicebooking.src.main.java.com.team.servicebooking.model.service.Service;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Client;
import servicebooking.src.main.java.com.team.servicebooking.model.user.Consultant;

import java.util.UUID;
import java.util.Date;


public class Booking {
    private UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private Availability availability;
    private Date bookingDate;
    private BookingState bookingState;
    private Payment payment;


    public Booking(Client client, Consultant consultant, Service service, Availability availabilty) {
    	this.booking_id = UUID.randomUUID();
    	this.client = client;
    	this.consultant = consultant;
    	this.service = service;
    	this.availability = availabilty;

    	//this.bookingDate = bookingDate;
    	//this.payment = payment;

    	this.bookingState = new RequestState(this);

    	System.out.println("Booking requested for " + client.getName() + ".");   //TODO: edit with date info.

    }

    public java.util.UUID getID() {
        return booking_id;
    }

    public void changeState(BookingState bookingState) { //TODO: I dont think we should explicitly pass the new state; the logic should work so that it knows what state to use next on its own
    	//Changed return type from boolean to void.
    	this.bookingState = bookingState;
    }


    public boolean payable() {
        return this.bookingState.payable();
    }

    public void cancel() {
    	this.bookingState.cancel();
    }

    public void reject() {
    	this.bookingState.reject();
    }

    public double getPrice() {
        return 0;
    }

    public void pay(Payment payment)
    {

    }

    public boolean paid() {
        return false;
    }
}
