package com.team.servicebooking.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.team.servicebooking.model.user.Consultant;

@Service
public class ConsultantService {
    
    private final Map<UUID, Consultant> consultants = new HashMap<>();

    public Consultant createConsultant(String name, String email, String password) {
        UUID id = UUID.randomUUID();
        Consultant consultant = new Consultant(id, name, email, password);

        // --- PHASE 1 DEMO SEEDING ---
        // Create a default Service and Availability slot so this consultant is instantly bookable!
        com.team.servicebooking.model.service.Service demoService = new com.team.servicebooking.model.service.Service(
                "Demo Consultation", "A default service for testing.", 150.0, 1);
        consultant.addService(demoService);

        com.team.servicebooking.model.availability.Availability demoSlot = new com.team.servicebooking.model.availability.Availability(
                java.time.LocalDateTime.now().plusDays(1), java.time.LocalDateTime.now().plusDays(1).plusHours(1));
        consultant.addAvailability(demoSlot);
        
        com.team.servicebooking.config.DatabaseSingleton.getInstance().addConsultant(consultant);
        // -----------------------------

        consultants.put(id, consultant);
        return consultant;
    }

    public List<Consultant> getAllConsultants() {
        return new ArrayList<>(consultants.values());
    }

    public Optional<Consultant> getConsultantById(UUID id) {
        return Optional.ofNullable(consultants.get(id));
    }
}
