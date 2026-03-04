package servicebooking.src.main.java.com.team.servicebooking.model.user;

import servicebooking.src.main.java.com.team.servicebooking.config.DatabaseSingleton;

import java.util.UUID;

public class Admin extends User {

    DatabaseSingleton database;

    public Admin(UUID user_id, String name, String email, String password) {
        super(user_id, name, email, password);
        database = DatabaseSingleton.getInstance();
        
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
