package com.team.servicebooking.model.payment;

public class Debit implements PaymentMethodStrategy {

    public boolean validate() {
        return false;
    }
}
