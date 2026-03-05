package com.team.servicebooking.model.booking;

public abstract class BookingState {
    Booking booking;

    public BookingState(Booking booking) {
        this.booking = booking;
    }

    public void request() {

    }

    public void confirm() {

    }

    public void reject() {

    }

    public void cancel() {
        // Can be edited depend on Admin's policy
    }

    public void markPaid() {

    }

    public void complete() {

    }

    public void pending() {

    }

    public boolean payable() {
        return false;
    }

}
