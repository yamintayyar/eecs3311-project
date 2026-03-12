package com.team.servicebooking.model.booking;

public class RejectedState extends BookingState {
	public RejectedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "REJECTED";
	}

	@Override
	public void confirm() {
		throw new IllegalStateException("Cannot confirm a rejected booking.");
	}

	@Override
	public void cancel() {
		throw new IllegalStateException("Cannot cancel a rejected booking.");
	}

	@Override
	public void complete() {
		throw new IllegalStateException("Cannot complete a rejected booking.");
	}

	@Override
	public void markPaid() {
		throw new IllegalStateException("Cannot pay a rejected booking.");
	}
}
