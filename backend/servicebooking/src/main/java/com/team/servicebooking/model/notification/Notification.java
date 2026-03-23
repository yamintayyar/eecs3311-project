package com.team.servicebooking.model.notification;

import com.team.servicebooking.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="notification")
public class Notification {

    @OneToOne
    private User user;
    private String message;

    public Notification() {

    }

    public Notification(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
