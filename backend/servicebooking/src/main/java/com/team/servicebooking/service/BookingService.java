package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.dto.BookingRequestDTO;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import org.springframework.stereotype.Component;

import java.util.*;

@org.springframework.stereotype.Service
public class BookingService {

    private final Map<UUID, Booking> bookings = new HashMap<>();
    private final ClientService clientService;

    public BookingService(ClientService clientService) {
        this.clientService = clientService;
    }

    public Booking createBooking(BookingRequestDTO request) {
        UUID clientId = UUID.fromString(request.getClientId());
        UUID consultantId = UUID.fromString(request.getConsultantId());
        UUID serviceId = UUID.fromString(request.getServiceId());
        List<String> slotIds = request.getSlotIds();

        // 1. Get Client
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // 2. Get Consultant
        Consultant consultant = DatabaseSingleton.getInstance().getConsultants().stream()
                .filter(c -> c.getID().equals(consultantId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        // 3. Get Service
        Service service = consultant.getServices().stream()
                .filter(s -> s.getServiceId().equals(serviceId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service not found for this consultant"));

        // 4. Get Availabilities
        List<Availability> availabilities = new ArrayList<>();
        for (String slotIdStr : slotIds) {
            UUID slotId = UUID.fromString(slotIdStr);
            Availability slot = consultant.getAvailabilities().stream()
                    .filter(a -> a.getSlotId().equals(slotId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Availability slot not found: " + slotId));
            
            if (slot.isBooked()) {
                throw new RuntimeException("Slot is already booked: " + slotId);
            }
            availabilities.add(slot);
        }

        // 5. Create Booking
        Booking booking = new Booking(client, consultant, service, availabilities);
        bookings.put(booking.getID(), booking);
        
        // Mark slots as booked
        for(Availability a : availabilities) {
            a.markBooked();
        }

        return booking;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    public Optional<Booking> getBookingById(UUID id) {
        return Optional.ofNullable(bookings.get(id));
    }
}
