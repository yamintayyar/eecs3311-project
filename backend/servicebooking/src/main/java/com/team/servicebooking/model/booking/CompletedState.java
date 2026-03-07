package com.team.servicebooking.model.booking;

public class CompletedState extends BookingState {
	public CompletedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "COMPLETED";
	}
}
