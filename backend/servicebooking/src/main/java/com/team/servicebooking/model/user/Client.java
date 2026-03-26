package com.team.servicebooking.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends User {

//    private DatabaseSingleton database;

    /*
     * @OneToMany(mappedBy = "client")
     *
     * @JsonManagedReference
     * private List<Booking> bookings = new ArrayList<>();
     */

    protected Client() {
    }

    public Client(String name, String email, String password) {
        super(name, email, password);
    }

}
