import java.util.List;


public class Client extends User {
    private List<PaymentMethodStrategy> paymentMethods;
    private List<Payment> payments;
    private List<Booking> bookings;
    private DatabaseSingleton database;

    Public Client(UUID user_id, String name, String email, String password) {
    	super(user_id, name, email, password);
    	
    }

    void addPaymentMethod(PaymentMethodStrategy paymentMethod) {

    }

    void removePaymentMethod(PaymentMethodStrategy paymentMethod) {

    }

    List<Booking> getBookingHistory() {
        return null;
    }

    List<Payment> getPaymentHistory() {
        return null;
    }

    void cancelBooking(Booking booking) {

    }

    void requestBooking(Consultant consultant, Availability availability, Service service) {

    }

    void processPayment(Booking booking, PaymentMethodStrategy paymentMethod) {

    }

    List<Booking> browseBookings() {
        return null;
    }


   
}
