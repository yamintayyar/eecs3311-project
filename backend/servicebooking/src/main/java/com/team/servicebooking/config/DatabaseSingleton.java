package servicebooking.src.main.java.com.team.servicebooking.config;

import servicebooking.src.main.java.com.team.servicebooking.model.user.Consultant;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSingleton {
    private static DatabaseSingleton databaseSingleton;
    private List<Consultant> consultants;
    private int min_notice = 24;
    private double discount = 1.0;
    private boolean refund = false;
    private boolean verbose = false;

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

    public int getMinNotice() {return min_notice;}

    public void setMinNotice(int min_notice){
        this.min_notice = min_notice;
    }

    public double applyDiscount() {return discount;}

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean getRefundPolicy() {
        return refund;
    }

    public void setRefundPolicy(boolean policy) {
        refund = policy;
    }

    public void addConsultant(Consultant consultant) {
    	consultants.add(consultant);
    }

    public void setVerboseNotifications(boolean setting) {
        verbose = setting;
    }

    public boolean getVerboseNotification() {
        return verbose;
    }
}