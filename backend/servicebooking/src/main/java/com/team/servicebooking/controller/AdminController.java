package com.team.servicebooking.controller;

import com.team.servicebooking.dto.AdminRequestDTO;
import com.team.servicebooking.dto.LoginRequestDTO;
import com.team.servicebooking.model.user.Admin;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.service.AdminService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public Admin createAdmin(@RequestBody AdminRequestDTO request) {
        return adminService.createAdmin(request.getName(),
                request.getEmail(),
                request.getPassword());
    }

    @PostMapping("/login")
    public Admin login(@RequestBody LoginRequestDTO request) {
        return adminService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping
    public List<Admin> getAllClients() {
        return adminService.getAllClients();
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable UUID id) {
        adminService.deleteAdmin(id);
    }

}
