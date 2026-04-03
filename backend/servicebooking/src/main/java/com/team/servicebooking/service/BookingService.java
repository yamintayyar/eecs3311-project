package com.team.servicebooking.service;

import com.team.servicebooking.config.DatabaseSingleton;
import com.team.servicebooking.dto.BookingRequestDTO;
import com.team.servicebooking.model.availability.Availability;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.model.booking.PendingPaymentState;
import com.team.servicebooking.model.service.Service;
import com.team.servicebooking.model.user.Client;
import com.team.servicebooking.model.user.Consultant;
import com.team.servicebooking.repository.AvailabilityRepository;
import com.team.servicebooking.repository.BookingRepository;
import com.team.servicebooking.repository.ConsultantRepository;
import com.team.servicebooking.repository.ServiceRepository;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

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
            NotificationService notificationService, ConfigService configService, PaymentService paymentService) {
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

        Consultant consultant = consultantRepository.findById(
                UUID.fromString(request.getConsultantId()))
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        Service service = serviceRepository.findById(
                UUID.fromString(request.getServiceId()))
                .orElseThrow(() -> new RuntimeException("Service not found"));

        List<Availability> slots = availabilityRepository.findAllById(
                request.getSlotIds().stream().map(UUID::fromString).toList());
        // .orElseThrow(() -> new RuntimeException("Invalid availability slot not found;
        // please try again."));; //TODO: perhaps it is better to use an iterative
        // approach instead, to facilitate error handling

        // int minNotice = configService.getConfiguration().getMinNotice();

        for (Availability slot : slots) {

            if (slot.isBooked()) {
                throw new RuntimeException("Slot already booked");
            }

            // long hours = Math.abs(Duration.between(LocalDateTime.now(),
            // slot.getStartTime()).toHours());
            //
            // if (hours < minNotice) { //TODO: something to be made aware of: minNotice was
            // meant to reflect cancellation deadlines; we can do this too, although it is
            // not a requirement
            // throw new RuntimeException("Booking must be made " + minNotice + " hours in
            // advance");
            // }

            slot.markBooked(); // TODO: maybe we should mark the availability booked only after the booking has
                               // been accepted?
        }

        Booking booking = new Booking(client, consultant, service, slots);

        notificationService.notify(client,
                "Your booking with consultant " + consultant.getName() + " is confirmed.");

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
        notificationService.notify(booking.getClient(),
                "Your booking has been cancelled.");

        notificationService.notify(booking.getConsultant(),
                "A booking has been cancelled.");

        bookingRepository.delete(booking);
    }

    public List<Booking> getBookingsByConsultant(UUID consultantId) {
        return null;// TODO
    }

    public void cancelBooking(UUID bookingId) {

        Booking booking = getBookingById(bookingId);
        DatabaseSingleton config = configService.getConfiguration();

        LocalDateTime now = LocalDateTime.now();
        int minNotice = config.getMinNotice();

        LocalDateTime earliestStart = booking.getAvailabilities()
                .stream()
                .map(Availability::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow(() -> new RuntimeException("No availability found"));

        long hoursUntilStart = Duration.between(now, earliestStart).toHours();

        if (hoursUntilStart < minNotice) {
            String message = "Cannot cancel booking. Minimum notice of "
                    + minNotice + " hours required.";

            notificationService.notify(booking.getClient(), message);
            throw new RuntimeException(message);
        }

        // Refund logic
        if (booking.getPayment() != null &&
                booking.getState().isRefundable() &&
                config.getRefundPolicy()) {

            paymentService.refund(booking.getPayment());

            notificationService.notify(
                    booking.getClient(),
                    "Payment refunded for booking " + bookingId);
        }

        booking.cancel();

        notificationService.notify(
                booking.getClient(),
                "Booking " + bookingId + " cancelled successfully");

        bookingRepository.save(booking);
    }

    public void reject(UUID bookingId) {
        Booking booking = getBookingById(bookingId);

        // Non-optional notification, as per guidelines
        notificationService.notify(booking.getClient(),
                "Booking " + bookingId + " has been rejected by consultant " + booking.getConsultant().getName());

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

}
