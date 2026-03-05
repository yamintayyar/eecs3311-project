package com.team.servicebooking.model.user;

import java.util.UUID;

import com.team.servicebooking.config.DatabaseSingleton;

public class Admin extends User {

    DatabaseSingleton database;

    public Admin(UUID user_id, String name, String email, String password) {
        super(user_id, name, email, password);
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
