import java.util.List;

public class Consultant extends User {
    private List<Service> services;
    private List<Availability>  availabilitySlots;
    private List<Booking> bookings;

    public Consultant(UUID user_id, String name, String email, String password) {
    	super(user_id, name, email, password);
    	
    }

    void addAvailability() {

    }

    void removeAvailability(Availability availability) {

    }

    List<Booking> viewBookings() {
        return null;
    }

    void acceptBooking(Booking booking) {

    }

    void rejectBooking(Booking booking) {

    }

    void completeBooking(Booking booking) {

    }

    List<Service> getServices() {
        return null;
    }

    List<Availability> getAvailabilities() {
        return null;
    }

    void notify(Booking booking) { //do we need this?

    }

    
}

