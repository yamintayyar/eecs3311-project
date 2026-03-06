package com.team.servicebooking.model.payment;

import java.time.LocalDate;
import java.util.UUID;

public interface PaymentMethodStrategy {

    /*
     * UUID method_id;
     * String number;
     * String number2;
     * LocalDate expiry;
     * String email;
     */

    boolean validate();

}
