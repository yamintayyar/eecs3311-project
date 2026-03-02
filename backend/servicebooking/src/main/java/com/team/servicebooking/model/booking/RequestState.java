package servicebooking.src.main.java.com.team.servicebooking.model.booking;


public class RequestState extends BookingState {
	public RequestState(Booking booking) {
		super(booking);
	}
	
	public void confirm() {
    	ConfirmedState confirmedState = new ConfirmedState(this.booking);
    	this.booking.changeState(confirmedState);
    	System.out.println("Request has been confirmed by consultant.");
    }
	
	public void reject() {
		RejectedState rejectedState = new RejectedState(this.booking);
		this.booking.changeState(rejectedState);
		System.out.println("Request has been rejected by consultant.");
    }
	
	public void cancel() {
		CancelledState cancelledState = new CancelledState(this.booking);
    	this.booking.changeState(cancelledState);
    	System.out.println("Booking cancelled by client.");
    }
}
