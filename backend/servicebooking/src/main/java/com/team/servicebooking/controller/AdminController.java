package com.team.servicebooking.controller;

import com.team.servicebooking.dto.AdminRequestDTO;
import com.team.servicebooking.model.user.Admin;
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

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable UUID id) {
        adminService.deleteAdmin(id);
    }

}
