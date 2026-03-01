import java.util.List;

public class DatabaseSingleton {
    private DatabaseSingleton databaseSingleton;
    private List<Consultant> consultants;

    private DatabaseSingleton() {

    }

    static DatabaseSingleton getInstance() {
        return null;
    }

    List<Consultant> getConsultants() {
        return null;
    }

    void addConsultant(Consultant consultant) {

    }
}
