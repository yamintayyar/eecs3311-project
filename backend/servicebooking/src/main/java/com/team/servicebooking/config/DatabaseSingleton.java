package com.team.servicebooking.config;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "config")
public class DatabaseSingleton {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private int min_notice = 24;
    private double discount = 1.0;
    private boolean refund = false;
    private boolean verbose = false;

    public DatabaseSingleton() {
    }



    //TODO: Is this even a singleton anymore, if we are getting objects via the database?

    public int getMinNotice() {
        return min_notice;
    }

    public void setMinNotice(int min_notice) {
        this.min_notice = min_notice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean getRefundPolicy() {
        return refund;
    }

    public void setRefundPolicy(boolean policy) {
        refund = policy;
    }

    public void setVerboseNotifications(boolean setting) {
        verbose = setting;
    }

    public boolean getVerboseNotification() {
        return verbose;
    }
}