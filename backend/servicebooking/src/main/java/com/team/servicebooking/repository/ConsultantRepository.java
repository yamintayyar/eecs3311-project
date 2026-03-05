package com.team.servicebooking.repository;

import com.team.servicebooking.model.user.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {
}