package servicebooking.src.main.java.com.team.servicebooking.model.booking;


public class PendingPaymentState extends BookingState{
	public PendingPaymentState(Booking booking) {
		super(booking);
	}
	
	public void markPaid() {
		PaidState paidState = new PaidState(this.booking);
		this.booking.changeState(paidState);
		System.out.println("Payment Approved.");
    }

	public void cancel() {
		
		CancelledState cancelledState = new CancelledState(this.booking);
		this.booking.changeState(cancelledState);
		System.out.println("Booking cancelled.");
    }

	public boolean payable() {
		return true;
	}
}
