package com.team.servicebooking.controller;

import com.team.servicebooking.dto.BookingRequestDTO;
import com.team.servicebooking.model.booking.Booking;
import com.team.servicebooking.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequestDTO request) {
        return bookingService.createBooking(request);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable UUID id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/client/{id}")
    public List<Booking> getAllBookingsByClient(@PathVariable UUID id) {
        return bookingService.getBookingsByClient(id);
    }

    @GetMapping("/consultant/{id}")
    public List<Booking> getAllBookingsByConsultant(@PathVariable UUID id) {
        return bookingService.getBookingsByConsultant(id);
    }

    @PostMapping("/{id}")
    public void updateBookingStatus(@PathVariable UUID id, @RequestBody String update) {
        update = update.toLowerCase().trim();

        System.out.println("msg = " + update);

        if (update.equals("cancel")) {
            bookingService.cancelBooking(id);
        } else if (update.equals("confirm")) {
            bookingService.confirm(id);
        } else if (update.equals("reject")) {
            bookingService.reject(id);
        } else if (update.equals("pending")) {
            bookingService.setPending(id);
        } else if (update.equals("paid")) {
            bookingService.setPaid(id);
        }
    }
}