package com.team.servicebooking.model.booking;

public class ConfirmedState extends BookingState {
	public ConfirmedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "CONFIRMED";
	}

	public void pending() {
		PendingPaymentState pendingPaymentState = new PendingPaymentState(this.booking);
		this.booking.changeState(pendingPaymentState);
		System.out.println("Payment in process.");
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
