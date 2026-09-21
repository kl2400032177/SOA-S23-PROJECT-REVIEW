package com.klu.booking.controller;

import com.klu.booking.dto.BookingRequest;
import com.klu.booking.entity.Reservation;
import com.klu.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Reservation> createBooking(@Valid @RequestBody BookingRequest request) {
        Reservation reservation = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @GetMapping("/guest/{email}")
    public ResponseEntity<List<Reservation>> getBookingsForGuest(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getBookingsForGuest(email));
    }

}