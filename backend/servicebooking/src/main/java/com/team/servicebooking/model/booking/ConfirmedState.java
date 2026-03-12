package com.team.servicebooking.model.booking;

public class ConfirmedState extends BookingState {
	public ConfirmedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "CONFIRMED";
	}

	@Override
	public void complete() {
		CompletedState completed = new CompletedState(this.booking);
		this.booking.changeState(completed);
		System.out.println("Booking completed successfully.");
	}

	public void cancel() {
		CancelledState cancelledState = new CancelledState(this.booking);
		this.booking.changeState(cancelledState);
		System.out.println("Booking cancelled.");
	}

	@Override
	public void markPaid() {
		System.out.println("Payment marked for confirmed booking.");
	}

	public boolean payable() {
		return true;
	}
}
