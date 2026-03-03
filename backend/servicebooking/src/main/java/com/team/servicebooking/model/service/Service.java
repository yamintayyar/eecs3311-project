package servicebooking.src.main.java.com.team.servicebooking.model.service;

public class Service {
    private java.util.UUID service_id;
    private String serviceName;
    private String service_description;
    private double price;
    private int duration;

    public double getPrice() {
        return price;
    }

    double getDuration() {
        return duration;
    }

    String getName() {
        return serviceName;
    }

    String getDescription() {
        return service_description;
    }
}
