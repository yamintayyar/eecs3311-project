package com.team.servicebooking.config;

import java.util.List;

import com.team.servicebooking.model.user.Consultant;

public class DatabaseSingleton {
    private DatabaseSingleton databaseSingleton;
    private List<Consultant> consultants;

    private DatabaseSingleton() {

    }

    DatabaseSingleton getInstance() {
        return null;
    }

    List<Consultant> getConsultants() {
        return null;
    }
}
