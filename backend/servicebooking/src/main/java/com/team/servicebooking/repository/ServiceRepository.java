package com.team.servicebooking.repository;

import com.team.servicebooking.model.service.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

}
