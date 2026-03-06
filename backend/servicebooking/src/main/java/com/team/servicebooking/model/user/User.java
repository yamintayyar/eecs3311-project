package com.team.servicebooking.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    protected UUID userId;

    protected String name;
    protected String email;
    protected String password;

    @ElementCollection
    protected List<String> notifications;

    public User() {

    }

    public User(UUID userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.notifications = new ArrayList<>();
    }

    public boolean login() {

        try {
            System.out.println("Success! Welcome, " + this.name);

            if (!notifications.isEmpty()) { // if there are notifications, it is printed as user logs in

                System.out.println("Notifications:");
                for (String s : notifications) {
                    System.out.println(s);
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Unable to fulfill request");
            return false;
        }
    }

    public boolean logout() {

        try {
            System.out.println("Goodbye, " + this.name);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Unable to fulfill request");
            return false;
        }
    }

    public UUID getID() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void notify(String notification) {
        if (notification != null) {
            notifications.add(notification);
        }
    }
}
