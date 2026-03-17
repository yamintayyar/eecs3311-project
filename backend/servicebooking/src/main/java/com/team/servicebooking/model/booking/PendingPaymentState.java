package com.team.servicebooking.model.booking;

public class PendingPaymentState extends BookingState {
	public PendingPaymentState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "PENDING_PAYMENT";
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

	// prevent invalid operations
	@Override
	public void confirm() {
		throw new IllegalStateException("Cannot confirm payment while pending.");
	}

	@Override
	public void complete() {
		throw new IllegalStateException("Cannot complete booking while payment is pending.");
	}

	public boolean payable() {
		return true;
	}
}
