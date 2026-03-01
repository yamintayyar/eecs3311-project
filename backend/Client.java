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

    }

    void processPayment(Booking booking, PaymentMethodStrategy paymentMethod) {

    }

    List<Booking> browseBookings() {
        return null;
    }


   
}
