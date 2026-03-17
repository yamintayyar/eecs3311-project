package com.team.servicebooking.model.booking;

public class CancelledState extends BookingState {
	public CancelledState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "CANCELLED";
	}

	@Override
	public void confirm() {
		throw new IllegalStateException("Cannot confirm a cancelled booking.");
	}

	@Override
	public void markPaid() {
		throw new IllegalStateException("Cannot pay a cancelled booking.");
	}

	@Override
	public void complete() {
		throw new IllegalStateException("Cannot complete a cancelled booking.");
	}

}
