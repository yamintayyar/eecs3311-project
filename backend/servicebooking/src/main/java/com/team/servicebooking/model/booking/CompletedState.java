package com.team.servicebooking.model.booking;

public class CompletedState extends BookingState {
	public CompletedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "COMPLETED";
	}

	@Override
	public void cancel() {
		throw new IllegalStateException("Cannot cancel a completed booking.");
	}

	@Override
	public void confirm() {
		throw new IllegalStateException("Cannot confirm a completed booking.");
	}

	@Override
	public void reject() {
		throw new IllegalStateException("Cannot reject a completed booking.");
	}

	@Override
	public void markPaid() {
		throw new IllegalStateException("Booking is already completed; payment should be done.");
	}

	@Override
	public void complete() {
		throw new IllegalStateException("Booking is already completed.");
	}
}
