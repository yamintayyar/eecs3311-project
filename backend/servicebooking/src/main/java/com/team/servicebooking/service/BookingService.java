package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.dto.BookingRequestDTO;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.BookingRepository;
import com.team.servicebooking.repository.ClientRepository;
import com.team.servicebooking.repository.ConsultantRepository;
import com.team.servicebooking.repository.ServiceRepository;
import com.team.servicebooking.repository.AvailabilityRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

@org.springframework.stereotype.Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientService clientService; //TODO: do we need all of these?
    private final ConsultantRepository consultantRepository;
    private final ServiceRepository serviceRepository;
    private final AvailabilityRepository availabilityRepository;
    private final NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository,
            ClientService clientService,
            ConsultantRepository consultantRepository,
            ServiceRepository serviceRepository,
            AvailabilityRepository availabilityRepository,
            NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.clientService = clientService;
        this.consultantRepository = consultantRepository;
        this.serviceRepository = serviceRepository;
        this.availabilityRepository = availabilityRepository;
        this.notificationService = notificationService;
    }

    public Booking createBooking(BookingRequestDTO request) {
        Client client = clientService.getClientById(UUID.fromString(request.getClientId()))
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Consultant consultant = consultantRepository.findById(
                UUID.fromString(request.getConsultantId()))
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        Service service = serviceRepository.findById(
                UUID.fromString(request.getServiceId()))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<Availability> slots = availabilityRepository.findAllById(
                request.getSlotIds().stream().map(UUID::fromString).toList())
                .orElseThrow(() -> new RuntimeException("Invalid availability slot not found; please try again."));;

        int minNotice = DatabaseSingleton.getInstance().getMinNotice();

        for (Availability slot : slots) {

            if (slot.isBooked()) {
                throw new RuntimeException("Slot already booked");
            }

            long hours = Math.abs(Duration.between(LocalDateTime.now(), slot.getStartTime()).toHours());

            if (hours < minNotice) { //TODO: something to be made aware of: minNotice was meant to reflect cancellation deadlines; we can do this too, although it is not a requirement
                throw new RuntimeException("Booking must be made " + minNotice + " hours in advance");
            }

            slot.markBooked(); //TODO: maybe we should mark the availability booked only after the booking has been accepted?
        }

        Booking booking = new Booking(client, consultant, service, slots);

        notificationService.notifyClient(
                "Your booking with consultant " + consultant.getName() + " is confirmed.");

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(UUID id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public void deleteBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.getAvailabilities().forEach(Availability::markAvailable);
        notificationService.notifyClient(
                "Your booking has been cancelled.");

        notificationService.notifyConsultant(
                "A booking has been cancelled.");

        bookingRepository.delete(booking);
    }
}
