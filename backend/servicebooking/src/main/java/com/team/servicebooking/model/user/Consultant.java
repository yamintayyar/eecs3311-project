package servicebooking.src.main.java.com.team.servicebooking.model.user;

import servicebooking.src.main.java.com.team.servicebooking.model.availability.Availability;
import servicebooking.src.main.java.com.team.servicebooking.model.booking.Booking;
import servicebooking.src.main.java.com.team.servicebooking.model.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Consultant extends User {
    private List<Service> services;
    private List<Availability>  availabilitySlots;
    private List<Booking> bookings;

    public Consultant(UUID user_id, String name, String email, String password) {
    	super(user_id, name, email, password);
    	
    	this.services = new ArrayList<Service>();
    	this.availabilitySlots = new ArrayList<Availability>();
    	this.bookings = new ArrayList<Booking>();
    	
    }

    public void addAvailability(Availability availability) {
        if (availability != null) availabilitySlots.add(availability);
    }

    public void removeAvailability(Availability availability) {
        availabilitySlots.remove(availability);
    }

    public List<Booking> viewBookings() {
        List<Booking> t = new ArrayList<Booking>();
        t.addAll(bookings); //deep copy of list, shallow copy of all bookings within the list
        return t;
    }

    public void acceptBooking(Booking booking) {

        if (!booking.paid()) booking.confirm(); //if at requestedstate, goes to confirmedstate.
        //if at confirmedstate, goes to pendingpaymentstate (basically equivalent)
        //otherwise, will not proceed because there is no payment yet
    }

    public void rejectBooking(Booking booking) {
        if (!booking.payable()) { //if booking is not payable (and therefore not yet confirmed or pending payment), consultant can reject it
            booking.reject();
        }
    }

    public void completeBooking(Booking booking) {
        if (booking.paid()) booking.complete(); //if booking already was paid for, we can move to final confirmed state
    }

    public List<Service> getServices() {
        List<Service> t = new ArrayList<>();
        t.addAll(services); //deep copy of list, shallow copy of all bookings within the list
        return t;
    }

    public List<Availability> getAvailabilities() {
        List<Availability> t = new ArrayList<>();
        t.addAll(availabilitySlots); //deep copy of list, shallow copy of all bookings within the list
        return t;
    }

    public void notify(String notification, Booking booking) {
        bookings.add(booking);

        this.notify(notification);

    }

    
}

