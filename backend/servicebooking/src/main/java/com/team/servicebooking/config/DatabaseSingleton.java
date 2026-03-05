package com.team.servicebooking.config;

import java.util.ArrayList;
import java.util.List;

import com.team.servicebooking.model.user.Consultant;

public class DatabaseSingleton {
    private static DatabaseSingleton databaseSingleton;
    private List<Consultant> consultants;

    private DatabaseSingleton() {
        consultants = new ArrayList<Consultant>();
    }

    public static DatabaseSingleton getInstance() {
        if (DatabaseSingleton.databaseSingleton == null) {
            DatabaseSingleton.databaseSingleton = new DatabaseSingleton();
        }
        return DatabaseSingleton.databaseSingleton;
    }

    public List<Consultant> getConsultants() {
        List<Consultant> t = new ArrayList<Consultant>();
        t.addAll(consultants);
        return t;
    }

    public void addConsultant(Consultant consultant) {
        consultants.add(consultant);
    }
}