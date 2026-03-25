package com.team.servicebooking.model.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="payment_method")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PaymentMethodStrategy {

    protected UUID method_id;
    protected String number;
    protected String number2;
    protected LocalDate expiry;
    protected String email;

    public abstract boolean validate();

    public abstract boolean pay(double amount) throws InterruptedException;

}
