package com.team.servicebooking.service;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.AvailabilityRepository;
import com.team.servicebooking.repository.ConsultantRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final AvailabilityRepository availabilityRepository;

    public ConsultantService(ConsultantRepository consultantRepository,
                             AvailabilityRepository availabilityRepository,
                             ServiceService serviceService) {
        this.consultantRepository = consultantRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Transactional
    public Consultant addConsultant(Consultant consultant) {
        return consultantRepository.save(consultant);
    }

    public Consultant login(String email, String password) {
        return consultantRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    @Transactional
    public Optional<Consultant> getConsultantById(UUID id) {
        return consultantRepository.findById(id);
    }

    @Transactional
public Availability addAvailabilityToConsultant(UUID consultantId,
                                                LocalDateTime startTime,
                                                LocalDateTime endTime) {
    if (startTime == null || endTime == null) {
        throw new RuntimeException("Start time and end time are required");
    }

    if (!endTime.isAfter(startTime)) {
        throw new RuntimeException("End time must be after start time");
    }

    Consultant consultant = consultantRepository.findById(consultantId)
            .orElseThrow(() -> new RuntimeException("Consultant not found"));

    Availability availability = new Availability(startTime, endTime);
    availability.setConsultant(consultant);
    consultant.addAvailability(availability);

    consultantRepository.save(consultant);

    return availability;
}

    @Transactional
    public void deleteConsultant(UUID id) {
        consultantRepository.deleteById(id);
    }
}