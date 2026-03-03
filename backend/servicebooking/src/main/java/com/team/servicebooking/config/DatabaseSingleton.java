package servicebooking.src.main.java.com.team.servicebooking.config;

import servicebooking.src.main.java.com.team.servicebooking.model.user.Consultant;

import java.util.List;

public class DatabaseSingleton {
    private DatabaseSingleton databaseSingleton;
    private List<Consultant> consultants;

    private DatabaseSingleton() {

    }

    public static DatabaseSingleton getInstance() {
        return null;
    }

    public List<Consultant> getConsultants() {
        return null;
    }

    public void addConsultant(Consultant consultant) {

    }
}