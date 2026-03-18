package com.team.servicebooking.model.booking;

public class PaidState extends BookingState {
	public PaidState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "PAID";
	}

	public void complete() {
		CompletedState completedState = new CompletedState(this.booking);
		this.booking.changeState(completedState);
		System.out.println("Booking Completed.");
	}

	public void cancel() {
		CancelledState cancelledState = new CancelledState(this.booking);
		this.booking.changeState(cancelledState);
		System.out.println("Booking cancelled; payment was already made");
	}

	@Override
	public boolean payable() {
		return false;
	}

	public boolean isRefundable() {
		return true;
	}

	// Block invalid operations
	@Override
	public void confirm() {
		throw new IllegalStateException("Booking is already paid and confirmed.");
	}

	@Override
	public void markPaid() {
		throw new IllegalStateException("Booking is already marked as paid.");
	}

	@Override
	public void reject() {
		throw new IllegalStateException("Cannot reject a paid booking.");
	}
}
