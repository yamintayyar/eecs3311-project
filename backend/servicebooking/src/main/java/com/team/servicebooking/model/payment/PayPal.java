package com.team.servicebooking.model.payment;

public class PayPal implements PaymentMethodStrategy {

    public boolean validate() {
        return false;
    }
}
