package com.team.servicebooking.service;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.ConsultantRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;

    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    /**
     * Creates a new Consultant and seeds a demo service and availability.
     */
    @Transactional
    public Consultant createConsultant(String name, String email, String password) {
        Consultant consultant = new Consultant(name, email, password);

        // --- PHASE 1 DEMO SEEDING ---
        Service demoService = new Service(
                "Demo Consultation",
                150.0,
                "A default service for testing.");
        consultant.addService(demoService);

        Availability demoSlot = new Availability(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1));
        consultant.addAvailability(demoSlot);
        // -----------------------------

        return consultantRepository.save(consultant); // persist consultant
    }

    public Consultant login(String email, String password) {

        return consultantRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email)
                        && c.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    public Optional<Consultant> getConsultantById(UUID id) {
        return consultantRepository.findById(id);
    }

    @Transactional
    public void deleteConsultant(UUID id) {
        consultantRepository.deleteById(id);
    }

}