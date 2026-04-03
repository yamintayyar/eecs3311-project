package com.team.servicebooking.controller;

import com.team.servicebooking.dto.AvailabilityRequestDTO;
import com.team.servicebooking.dto.ConsultantRequestDTO;
import com.team.servicebooking.dto.LoginRequestDTO;
import com.team.servicebooking.model.availability.Availability;
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
        Consultant consultant = new Consultant(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return consultantService.addConsultant(consultant);
    }

    @PostMapping("/login")
    public Consultant login(@RequestBody LoginRequestDTO request) {
        return consultantService.login(request.getEmail(), request.getPassword());
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

    @PostMapping("/{id}/availabilities")
    public Availability addAvailability(@PathVariable UUID id,
                                        @RequestBody AvailabilityRequestDTO request) {
        return consultantService.addAvailabilityToConsultant(
                id,
                request.getStartTime(),
                request.getEndTime()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteConsultant(@PathVariable UUID id) {
        consultantService.deleteConsultant(id);
    }
}