package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.dto.BookingRequestDTO;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.booking.PaidState;
import com.team.servicebooking.model.booking.PendingPaymentState;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.AvailabilityRepository;
import com.team.servicebooking.repository.BookingRepository;
import com.team.servicebooking.repository.ConsultantRepository;
import com.team.servicebooking.repository.ServiceRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientService clientService;
    private final ConsultantRepository consultantRepository;
    private final ServiceRepository serviceRepository;
    private final AvailabilityRepository availabilityRepository;
    private final NotificationService notificationService;
    private final ConfigService configService;
    private final PaymentService paymentService;

    public BookingService(BookingRepository bookingRepository,
                          ClientService clientService,
                          ConsultantRepository consultantRepository,
                          ServiceRepository serviceRepository,
                          AvailabilityRepository availabilityRepository,
                          NotificationService notificationService,
                          ConfigService configService,
                          PaymentService paymentService) {
        this.bookingRepository = bookingRepository;
        this.clientService = clientService;
        this.consultantRepository = consultantRepository;
        this.serviceRepository = serviceRepository;
        this.availabilityRepository = availabilityRepository;
        this.notificationService = notificationService;
        this.configService = configService;
        this.paymentService = paymentService;
    }

    public Booking createBooking(BookingRequestDTO request) {

        System.out.println("client is " + request.getClientId());
        System.out.println("cons is " + request.getConsultantId());
        System.out.println("ser is " + request.getServiceId());

        Client client = clientService.getClientById(UUID.fromString(request.getClientId()))
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Consultant consultant = consultantRepository.findById(UUID.fromString(request.getConsultantId()))
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        Service service = serviceRepository.findById(UUID.fromString(request.getServiceId()))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<Availability> slots = availabilityRepository.findAllById(
                request.getSlotIds().stream().map(UUID::fromString).toList()
        );

        if (slots.isEmpty()) {
            throw new RuntimeException("No availability slots selected");
        }

        for (Availability slot : slots) {
            if (slot.isBooked()) {
                throw new RuntimeException("Slot already booked");
            }

            if (slot.getConsultant() == null || !slot.getConsultant().getID().equals(consultant.getID())) {
                throw new RuntimeException("One or more slots do not belong to this consultant");
            }

            slot.markBooked();
        }

        Booking booking = new Booking(client, consultant, service, slots);

        notificationService.notify(
                client,
                "Your booking with consultant " + consultant.getName() + " is confirmed."
        );

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public void deleteBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.getAvailabilities().forEach(Availability::markAvailable);

        notificationService.notify(
                booking.getClient(),
                "Your booking has been cancelled."
        );

        notificationService.notify(
                booking.getConsultant(),
                "A booking has been cancelled."
        );

        bookingRepository.delete(booking);
    }

    public List<Booking> getBookingsByConsultant(UUID consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        return bookingRepository.findAllByConsultant(consultant);
    }

    public void cancelBooking(UUID bookingId) {
        Booking booking = getBookingById(bookingId);

        DatabaseSingleton config = configService.getConfiguration();
        int min_notice = config.getMinNotice();

        // Future deadline validation can go here

        if (booking.getState().isRefundable() && config.getRefundPolicy()) {
            paymentService.refund(booking.getPayment());
            System.out.println("Successfully refunded payment for booking " + bookingId);

            if (config.getVerboseNotification()) {
                notificationService.notify(
                        booking.getClient(),
                        "Successfully refunded payment for booking " + bookingId
                );
            }
        }

        booking.cancel();

        System.out.println("booking " + bookingId + " cancelled, status = " + booking.getStatus());

        bookingRepository.save(booking);
    }

    public void reject(UUID bookingId) {
        Booking booking = getBookingById(bookingId);

        notificationService.notify(
                booking.getClient(),
                "Booking " + bookingId + " has been rejected by consultant " + booking.getConsultant().getName()
        );

        booking.reject();
        bookingRepository.save(booking);
    }

    public void confirm(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.confirm();
        bookingRepository.save(booking);
    }

    public void setPending(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.changeState(new PendingPaymentState(booking));
        bookingRepository.save(booking);
    }

    public void setPaid(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.changeState(new PaidState(booking));
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByClient(UUID clientId) {
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return bookingRepository.findAllByClient(client);
    }
}