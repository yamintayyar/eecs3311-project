package com.team.servicebooking.model.payment;

public class BankTransfer implements PaymentMethodStrategy {

    public boolean validate() {
        return false;
    }
}
