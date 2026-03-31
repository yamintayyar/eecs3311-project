package com.team.servicebooking.service;

import com.team.servicebooking.dto.ServiceRequestDTO;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.ConsultantRepository;
import com.team.servicebooking.repository.ServiceRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ConsultantRepository consultantRepository;


    public ServiceService(ServiceRepository serviceRepository, ConsultantRepository consultantRepository) {
        this.serviceRepository = serviceRepository;
        this.consultantRepository = consultantRepository;
    }

    @Transactional
    public Optional<com.team.servicebooking.model.service.Service> getService(UUID service_id) {
        return serviceRepository.findById(service_id);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Transactional
    public List<Service> getServices(UUID consultant_id) {
        //TODO
        return null;
    }

    @Transactional
    public void addService(ServiceRequestDTO request) {
        Consultant consultant = consultantRepository.findById(UUID.fromString(request.getConsultantId()))
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        Service service = new Service(request.getName(), request.getPrice(), request.getDescription(), consultant);
        serviceRepository.save(service);

        //add service to consultant's list of services
        consultant.addService(service);
        consultantRepository.save(consultant);
    }
}
