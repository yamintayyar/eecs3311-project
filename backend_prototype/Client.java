import java.util.List;

public class Client implements User {
    private List<PaymentMethodStrategy> paymentMethods;
    private List<Payment> payments;
    private List<Booking> bookings;
    private DatabaseSingleton database;

    Client() {

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


    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
