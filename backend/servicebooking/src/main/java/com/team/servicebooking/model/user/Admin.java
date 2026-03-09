package com.team.servicebooking.model.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.team.servicebooking.config.DatabaseSingleton;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {

    @Transient
    private DatabaseSingleton database;

    public Admin() {
        // JPA requires empty constructor
    }

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    void approveConsultant(Consultant consultant) {
        database.addConsultant(consultant);
    }

    void setCancellationDeadline(int hours_prior) {
        database.setMinNotice(hours_prior);
    }

    void setDiscount(double new_rate) {
        database.setDiscount(new_rate);
    }

}
