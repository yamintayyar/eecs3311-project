package com.team.servicebooking.model.booking;

public class RejectedState extends BookingState {
	public RejectedState(Booking booking) {
		super(booking);
	}

	@Override
	public String getStatus() {
		return "REJECTED";
	}
}
