package com.team.servicebooking.config;

public class DatabaseSingleton {
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