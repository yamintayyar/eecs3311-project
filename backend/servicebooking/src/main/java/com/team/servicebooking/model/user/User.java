package servicebooking.src.main.java.com.team.servicebooking.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class User {
    protected UUID user_id;
    protected String name;
    protected String email;
    protected String password;
    protected List<String> notifications;
    
    public User(UUID user_id, String name, String email, String password) {
    	this.user_id = user_id;
    	this.name = name;
    	this.email = email;
    	this.password = password;
        this.notifications = new ArrayList<>();
    }

    public boolean login() {
    	
    	try {
            System.out.println("Success! Welcome, " + this.name);

            if (!notifications.isEmpty()) { //if there are notifications, it is printed as user logs in

                System.out.println("Notifications:");
                for (String s : notifications) {
                    System.out.println(s);
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Unable to fulfill request");
            return false;
        }
    }
    
    public boolean logout() {

        try {
            System.out.println("Goodbye, " + this.name);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Unable to fulfill request");
            return false;
        }
    }

    public void notify(String notification) {
        if (notification != null) {
            notifications.add(notification);
        }
    }
}

