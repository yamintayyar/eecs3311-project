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

//        ServiceRequestDTO demoService = new ServiceRequestDTO();
//        demoService.setConsultantId(consultant.getId().toString());
//        demoService.setDescription("A default service for testing.");
//        demoService.setName("Demo Consultation");
//        demoService.setPrice(150.0);
//        demoService.setDurationHours(1);
//
//        serviceService.addService(demoService);

//        Availability demoSlot = new Availability( //TODO: fix so that it uses database for demo
//                LocalDateTime.now().plusDays(1),
//                LocalDateTime.now().plusDays(1).plusHours(1));
//        consultant.addAvailability(demoSlot);

//        com.team.servicebooking.config.DatabaseSingleton.getInstance().addConsultant(consultant);
        // -----------------------------

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