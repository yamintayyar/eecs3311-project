package com.team.servicebooking.repository;

import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    public List<Booking> findAllByClient(Client client);

}
