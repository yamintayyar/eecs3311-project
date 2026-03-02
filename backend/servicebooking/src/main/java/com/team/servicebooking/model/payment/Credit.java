package com.team.servicebooking.model.payment;

public class Credit implements PaymentMethodStrategy {

    public boolean validate() {
        return false;
    }
}