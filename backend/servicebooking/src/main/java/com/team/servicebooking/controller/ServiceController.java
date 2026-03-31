package com.team.servicebooking.controller;

import com.team.servicebooking.dto.ServiceRequestDTO;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.service.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @PostMapping
    public void addService(@RequestBody ServiceRequestDTO request) throws InterruptedException {
        serviceService.addService(request);
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public Service getService(@PathVariable UUID id) {
        return serviceService.getService(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
