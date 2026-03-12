package com.team.servicebooking.model.payment;

import java.time.LocalDate;
import java.util.UUID;

public abstract class PaymentMethodStrategy {

    protected UUID method_id;
    protected String number;
    protected String number2;
    protected LocalDate expiry;
    protected String email;

    public abstract boolean validate();

    public abstract boolean pay(double amount) throws InterruptedException;

}
