package com.team.servicebooking.model.user;

import com.team.servicebooking.config.DatabaseSingleton;

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
