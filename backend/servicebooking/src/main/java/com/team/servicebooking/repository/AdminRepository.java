package com.team.servicebooking.repository;

import com.team.servicebooking.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}