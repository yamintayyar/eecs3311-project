import java.security.Provider;

public class Booking {
    private java.util.UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private Availability availability;
    private Booking booking;
    private BookingState bookingState;
    private Payment payment;

    boolean changeState() {
        return false;
    }

}
