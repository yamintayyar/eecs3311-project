package com.team.servicebooking.model.payment;

import com.team.servicebooking.model.user.Client;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="payment_method")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PaymentMethodStrategy {

    @Id
    @GeneratedValue
    protected UUID method_id;
    protected String number;
    protected String number2;
    protected LocalDate expiry;
    protected String email;

    @ManyToOne
    @JoinColumn(name = "client_id")
    protected Client client; //we need access to the client, to be able to fetch all relevant payment methods for a given client

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public abstract boolean validate();

    public abstract boolean pay(double amount) throws InterruptedException;

}
