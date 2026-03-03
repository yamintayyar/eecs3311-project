package servicebooking.src.main.java.com.team.servicebooking.model.availability;

import java.time.LocalDateTime;
import java.util.UUID;

public class Availability {
    private UUID slot_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked = false;

    Availability(LocalDateTime startTime, LocalDateTime endTime) {

    }

    void markBooked() {

    }

    void markAvailable() {

    }

}
