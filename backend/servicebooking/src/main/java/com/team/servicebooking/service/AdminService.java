package com.team.servicebooking.service;

import com.team.servicebooking.model.user.Admin;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ConsultantService consultantService;

    public AdminService(AdminRepository adminRepository, ConsultantService consultantService) {
        this.adminRepository = adminRepository;
        this.consultantService = consultantService;
    }

    @Transactional
    public Admin createAdmin(String name, String email, String password) {
        Admin admin = new Admin(name, email, password);
        return adminRepository.save(admin);
    }

    public Admin login(String email, String password) {

        return adminRepository.findAll().stream()
                .filter(c -> c.getEmail().equals(email)
                        && c.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<Admin> getAllClients() {
        return adminRepository.findAll();
    }

    @Transactional
    public void deleteAdmin(UUID id) {
        adminRepository.deleteById(id);
    }

}
