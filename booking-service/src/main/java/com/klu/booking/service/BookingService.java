package com.klu.booking.service;

import com.klu.booking.client.RoomServiceClient;
import com.klu.booking.dto.AvailabilityResponse;
import com.klu.booking.dto.BlockDateRequest;
import com.klu.booking.dto.BookingRequest;
import com.klu.booking.entity.Reservation;
import com.klu.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final RoomServiceClient roomServiceClient;

    public Reservation createBooking(BookingRequest request) {
        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            throw new IllegalArgumentException("checkOut must be after checkIn");
        }

        AvailabilityResponse availability;
        try {
            availability = roomServiceClient.checkAvailability(
                    request.getRoomId(), request.getCheckIn(), request.getCheckOut());
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not reach Room Service: " + e.getMessage());
        }

        if (!availability.isAvailable()) {
            throw new IllegalArgumentException("Room is not available for the requested dates");
        }

        Reservation reservation = new Reservation();
        reservation.setRoomId(request.getRoomId());
        reservation.setGuestEmail(request.getGuestEmail());
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        reservation.setStatus("CONFIRMED");
        reservation = reservationRepository.save(reservation);

        try {
            roomServiceClient.blockDates(new BlockDateRequest(
                    request.getRoomId(), request.getCheckIn(), request.getCheckOut(), reservation.getId()));
        } catch (Exception e) {
            reservationRepository.delete(reservation);
            throw new IllegalArgumentException("Could not lock the room for these dates: " + e.getMessage());
        }

        return reservation;
    }

    public Reservation cancelBooking(Long bookingId) {
        Reservation reservation = reservationRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        if ("CANCELLED".equals(reservation.getStatus())) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        reservation.setStatus("CANCELLED");
        reservationRepository.save(reservation);

        roomServiceClient.releaseDates(bookingId);

        return reservation;
    }

    public List<Reservation> getBookingsForGuest(String guestEmail) {
        return reservationRepository.findByGuestEmail(guestEmail);
    }

    public Reservation getBooking(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));
    }

}