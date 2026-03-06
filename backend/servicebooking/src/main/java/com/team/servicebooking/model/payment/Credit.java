package com.team.servicebooking.model.payment;

import java.time.LocalDate;

public class Credit implements PaymentMethodStrategy {
    private String number;
    private LocalDate expiry;
    private String cvv;

    public Credit(String number, LocalDate expiry, String cvv) {
        this.number = number;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public boolean validate() {

        // validate number
        if (!number.strip().matches("\\d{16}"))
            return false; // ensures string is 16 digits

        // validate cvv
        if (!cvv.strip().matches("\\d{3}"))
            return false; // ensures string is 3 digits

        // validate expiry
        if (expiry.compareTo(LocalDate.now()) < 0)
            return false; // ensures expiry date is after the current date

        return true;
    }

}