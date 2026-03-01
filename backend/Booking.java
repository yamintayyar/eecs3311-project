public class Booking {
    private java.util.UUID booking_id;
    private Client client;
    private Consultant consultant;
    private Service service;
    private Availability availability;
    private Booking booking;
    private BookingState bookingState;
    private Payment payment;

    public java.util.UUID getID() {
        return booking_id;
    }

    boolean changeState() {
        return false;
    }

    void cancel() {

    }

    void reject() {

    }

}
