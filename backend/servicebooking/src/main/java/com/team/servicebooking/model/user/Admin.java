package com.team.servicebooking.model.user;

public class Admin implements User {
    DatabaseSingleton database;

    public Admin() {

    }

    void approveConsultant(Consultant consultant) {

    }

    void createPolicy() {

    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }
}
