package com.team.servicebooking.repository;

import com.team.servicebooking.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
