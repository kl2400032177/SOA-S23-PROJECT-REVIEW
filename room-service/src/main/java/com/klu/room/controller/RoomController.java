package com.klu.room.controller;

import com.klu.room.dto.AvailabilityResponse;
import com.klu.room.dto.BlockDateRequest;
import com.klu.room.dto.RoomRequest;
import com.klu.room.entity.Room;
import com.klu.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomRequest request) {
        Room room = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms(@RequestParam(required = false) Long propertyId) {
        if (propertyId != null) {
            return ResponseEntity.ok(roomService.getRoomsByProperty(propertyId));
        }
        return ResponseEntity.ok(roomService.getRoomsByProperty(null) == null ? List.of() : List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<AvailabilityResponse> checkAvailability(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        boolean available = roomService.isAvailable(id, checkIn, checkOut);
        return ResponseEntity.ok(new AvailabilityResponse(id, available));
    }

    @PostMapping("/block")
    public ResponseEntity<Map<String, String>> blockDates(@Valid @RequestBody BlockDateRequest request) {
        roomService.blockDates(request);
        return ResponseEntity.ok(Map.of("status", "blocked"));
    }

    @DeleteMapping("/release/{bookingId}")
    public ResponseEntity<Map<String, String>> releaseDates(@PathVariable Long bookingId) {
        roomService.releaseDates(bookingId);
        return ResponseEntity.ok(Map.of("status", "released"));
    }

}