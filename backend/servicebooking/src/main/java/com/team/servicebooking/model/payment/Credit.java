package com.team.servicebooking.model.payment;

import java.time.LocalDate;

public class Credit extends PaymentMethodStrategy {

    public Credit(String number, LocalDate expiry, String cvv) {
        this.number = number;
        this.expiry = expiry;
        this.number2 = cvv;
    }

    public boolean validate() {

        // validate number
        if (!number.strip().matches("\\d{16}"))
            return false; // ensures string is 16 digits

        // validate cvv
        if (!number2.strip().matches("\\d{3}"))
            return false; // ensures string is 3 digits

        // validate expiry
        if (expiry.compareTo(LocalDate.now()) < 0)
            return false; // ensures expiry date is after the current date

        return true;
    }

}