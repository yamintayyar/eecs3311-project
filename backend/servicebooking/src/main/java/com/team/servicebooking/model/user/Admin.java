package com.team.servicebooking.model.user;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import com.team.servicebooking.config.DatabaseSingleton;

import jakarta.persistence.*;

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


//    void approveConsultant(Consultant consultant) {
//        database.addConsultant(consultant);
//    }
//
//    void setCancellationDeadline(int hours_prior) {
//        database.setMinNotice(hours_prior);
//    }
//
//    void setDiscount(double new_rate) {
//        database.setDiscount(new_rate);
//    }

}
