package com.team.servicebooking.repository;

import com.team.servicebooking.model.service.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

    @Query("SELECT s FROM Service s WHERE s.consultant.user_id = :consultantId")
    List<Service> findByConsultantId(UUID consultantId);

    @Query("SELECT s FROM Service s WHERE s.id = :serviceId AND s.consultant.user_id = :consultantId")
    Optional<Service> findByIdAndConsultantId(UUID serviceId, UUID consultantId);
}