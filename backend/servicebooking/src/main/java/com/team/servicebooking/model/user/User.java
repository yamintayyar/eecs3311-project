package servicebooking.src.main.java.com.team.servicebooking.model.user;

import java.util.UUID;

public abstract class User {
    protected UUID user_id;
    protected String name;
    protected String email;
    protected String password;
    
    public User(UUID user_id, String name, String email, String password) {
    	this.user_id = UUID.randomUUID();
    	this.name = "";
    	this.email = "";
    	this.password = "";
    
    }

    public boolean login() {
    	
    	try {
            System.out.println("Success! Welcome, " + this.name);
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
    
    public String getName() {
    	return this.name;
    }
}

