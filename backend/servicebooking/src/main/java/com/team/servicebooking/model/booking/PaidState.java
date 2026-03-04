package servicebooking.src.main.java.com.team.servicebooking.model.booking;


public class PaidState extends BookingState{
	public PaidState(Booking booking) {
		super(booking);
	}
	
	public void complete() {
		CompletedState completedState = new CompletedState(this.booking);
		this.booking.changeState(completedState);
		System.out.println("Booking Completed.");
    }

	public void cancel() {
		CancelledState cancelledState = new CancelledState(this.booking);
		this.booking.changeState(cancelledState);
		System.out.println("Booking cancelled.");
    }

	@Override
	public boolean isRefundable() {
		return true;
	}
}
