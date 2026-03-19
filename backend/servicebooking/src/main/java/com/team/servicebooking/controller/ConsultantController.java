package com.team.servicebooking.controller;

import com.team.servicebooking.dto.ConsultantRequestDTO;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.service.ConsultantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @PostMapping
    public Consultant createConsultant(@RequestBody ConsultantRequestDTO request) {
        Consultant consultant = new Consultant(request.getName(), request.getEmail(), request.getPassword());
        return consultantService.addConsultant(consultant);
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

    @DeleteMapping("/{id}")
    public void deleteConsultant(@PathVariable UUID id) {
        consultantService.deleteConsultant(id);
    }

}
