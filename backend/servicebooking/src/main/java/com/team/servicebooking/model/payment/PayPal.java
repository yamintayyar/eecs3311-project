package com.team.servicebooking.model.payment;

public class PayPal extends PaymentMethodStrategy {

    public PayPal(String email) {
        this.email = email;
    }

    public boolean validate() {

        return email.matches("\\p{Alnum}+@\\p{Alnum}+.[a-zA-Z]+"); // ensures email is in format
                                                                   // {alphanumeric}@{alphanumeric}.{alphabetical},
                                                                   // which matches with any domain

    }

    @Override
    public boolean pay(double amount) throws InterruptedException {

        if (!validate()) {
            return false;
        }

        System.out.println("Processing PAYPAL payment of $" + amount);

        Thread.sleep(500);

        return true;
    }
}
