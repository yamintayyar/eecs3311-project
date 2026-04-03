package com.team.servicebooking.repository;

import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findAllByClient(Client client);

    List<Booking> findAllByConsultant(Consultant consultant);
}