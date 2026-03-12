package com.team.servicebooking.model.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.team.servicebooking.config.DatabaseSingleton;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
        // JPA requires empty constructor
    }

    public Admin(String name, String email, String password) {
        super(name, email, password);

    }

}
