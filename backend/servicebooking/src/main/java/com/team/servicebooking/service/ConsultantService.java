package com.team.servicebooking.service;

import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.ConsultantRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ServiceService serviceService;

    public ConsultantService(ConsultantRepository consultantRepository, ServiceService serviceService) {
        this.consultantRepository = consultantRepository;
        this.serviceService = serviceService;
    }

    /**
     * Creates a new Consultant and seeds a demo service and availability.
     */
    @Transactional
    public Consultant addConsultant(Consultant consultant) {

        return consultantRepository.save(consultant); // persist consultant
    }

    @Transactional
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    @Transactional
    public Optional<Consultant> getConsultantById(UUID id) {
        return consultantRepository.findById(id);
    }

    @Transactional
    public void deleteConsultant(UUID id) {
        consultantRepository.deleteById(id);
    }

}