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
    	
    	return false;
    }
    
    public boolean logout() {
    	
    	return false;
    }
}

