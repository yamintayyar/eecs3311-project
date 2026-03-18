package com.team.servicebooking.service;

import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.repository.ServiceRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;


    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public Optional<com.team.servicebooking.model.service.Service> getService(UUID service_id) {
        return serviceRepository.findById(service_id);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
}
