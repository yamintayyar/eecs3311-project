package com.team.servicebooking.model.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BookingState {
    
    @JsonIgnore
    Booking booking;

    public BookingState(Booking booking) {
        this.booking = booking;
    }

    public abstract String getStatus();

    public void request() {}
    public void confirm() {}
    public void reject() {}
    public void cancel() {}
    public void markPaid() {}
    public void complete() {}
    public void pending() {}

    public boolean payable() {
        return false;
    }

    public boolean isRefundable() {
        return false;
    }
}
