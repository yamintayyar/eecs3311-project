package com.team.servicebooking.model.user;

import java.util.UUID;

import com.team.servicebooking.config.DatabaseSingleton;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {

    @Id
    @GeneratedValue
    private UUID userId;

    DatabaseSingleton database;

    public Admin(UUID user_id, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        database = DatabaseSingleton.getInstance();

    }

    void approveConsultant(Consultant consultant) {
        database.addConsultant(consultant);
    }

    void createPolicy() {
        // TODO: modify UML + classes so that changes in database attributes
        // (priceMultiplier, refundRule, etc.) are reflected in other classes
    }

}
