package com.team.servicebooking.service;

import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.AvailabilityRepository;
import com.team.servicebooking.repository.ConsultantRepository;
import com.team.servicebooking.repository.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@org.springframework.stereotype.Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;

    private static final Map<String, Double> FIXED_SERVICES = createFixedServices();

    public ConsultantService(ConsultantRepository consultantRepository,
                             AvailabilityRepository availabilityRepository,
                             ServiceRepository serviceRepository) {
        this.consultantRepository = consultantRepository;
        this.availabilityRepository = availabilityRepository;
        this.serviceRepository = serviceRepository;
    }

    private static Map<String, Double> createFixedServices() {
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("Service 1", 50.0);
        map.put("Service 2", 100.0);
        map.put("Service 3", 150.0);
        map.put("Service 4", 75.0);
        map.put("Service 5", 120.0);
        return Collections.unmodifiableMap(map);
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

    public Map<String, Double> getFixedServiceCatalog() {
        return FIXED_SERVICES;
    }

    @Transactional
    public List<Service> getConsultantServices(UUID consultantId) {
        consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        return serviceRepository.findByConsultantId(consultantId);
    }

    @Transactional
    public List<Service> replaceConsultantServices(UUID consultantId, List<String> selectedServiceNames) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        List<Service> existingServices = serviceRepository.findByConsultantId(consultantId);

        if (!existingServices.isEmpty()) {
            serviceRepository.deleteAll(existingServices);
        }

        consultant.clearServices();

        if (selectedServiceNames == null) {
            consultantRepository.save(consultant);
            return new ArrayList<>();
        }

        List<Service> newServices = new ArrayList<>();

        for (String serviceName : selectedServiceNames) {
            if (!FIXED_SERVICES.containsKey(serviceName)) {
                throw new RuntimeException("Invalid service selected: " + serviceName);
            }

            Service service = new Service(
                    serviceName,
                    FIXED_SERVICES.get(serviceName),
                    serviceName + " offered by consultant",
                    consultant
            );

            consultant.addService(service);
            newServices.add(service);
        }

        consultantRepository.save(consultant);
        return serviceRepository.findByConsultantId(consultantId);
    }

    @Transactional
    public Availability addAvailabilityToConsultant(UUID consultantId,
                                                    LocalDateTime startTime,
                                                    LocalDateTime endTime,
                                                    UUID serviceId) {
        if (startTime == null || endTime == null) {
            throw new RuntimeException("Start time and end time are required");
        }

        if (!endTime.isAfter(startTime)) {
            throw new RuntimeException("End time must be after start time");
        }

        if (serviceId == null) {
            throw new RuntimeException("A service must be selected");
        }

        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        Service service = serviceRepository.findByIdAndConsultantId(serviceId, consultantId)
                .orElseThrow(() -> new RuntimeException("Selected service does not belong to this consultant"));

        Availability availability = new Availability(startTime, endTime);
        availability.setConsultant(consultant);
        availability.setService(service);

        consultant.addAvailability(availability);
        consultantRepository.save(consultant);

        return availability;
    }

    @Transactional
    public void deleteConsultant(UUID id) {
        consultantRepository.deleteById(id);
    }
}