package com.team.servicebooking.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends User {

//    pre-database constructor
//    DatabaseSingleton database;
//
//    public Admin(UUID user_id, String name, String email, String password) {
//        super(user_id, name, email, password);
//        database = DatabaseSingleton.getInstance();
//
//    }

    public Admin() {
        // JPA requires empty constructor
    }

    public Admin(String name, String email, String password) {
        super(name, email, password);

    }


//
//    void setDiscount(double new_rate) {
//        database.setDiscount(new_rate);
//    }

}
