import java.util.List;

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
