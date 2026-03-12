package com.team.servicebooking.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.team.servicebooking.model.availability.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

}
