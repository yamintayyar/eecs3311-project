package com.team.servicebooking.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.servicebooking.dto.ConsultantRequestDTO;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.service.ConsultantService;

@RestController
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @PostMapping
    public Consultant createConsultant(@RequestBody ConsultantRequestDTO request) {
        return consultantService.createConsultant(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
    }

    @GetMapping
    public List<Consultant> getAllConsultants() {
        return consultantService.getAllConsultants();
    }

    @GetMapping("/{id}")
    public Consultant getConsultant(@PathVariable UUID id) {
        return consultantService.getConsultantById(id)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));
    }

}
