package com.team.servicebooking.model.payment;

public class BankTransfer extends PaymentMethodStrategy {

    public BankTransfer(String accountNumber, String routingNumber) {
        this.number = accountNumber;
        this.number2 = routingNumber;
    }

    public boolean validate() {
        if (!number.matches("\\d{5}-\\d{3}-\\d{7,12}"))
            return false; // ensures format XXXXX-YYY-ZZZZZZZ(...) for bank account number

        if (!number2.matches("0\\d{3}-\\d{5}"))
            return false; // ensures format 0YYY-XXXXX for routing number

        return true;
    }

    @Override
    public boolean pay(double amount) throws InterruptedException {

        if (!validate()) {
            return false;
        }

        System.out.println("Processing BANK_TRANSFER payment of $" + amount);

        Thread.sleep(500);

        return true;
    }
}
