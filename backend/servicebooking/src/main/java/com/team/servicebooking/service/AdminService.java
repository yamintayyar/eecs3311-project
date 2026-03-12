package com.team.servicebooking.service;

import com.team.servicebooking.model.user.Admin;
import com.team.servicebooking.repository.AdminRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public Admin createAdmin(String name, String email, String password) {
        Admin admin = new Admin(name, email, password);
        return adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(UUID id) {
        adminRepository.deleteById(id);
    }

}
