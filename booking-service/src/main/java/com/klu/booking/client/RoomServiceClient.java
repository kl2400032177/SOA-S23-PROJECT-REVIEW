package com.klu.booking.client;

import com.klu.booking.dto.AvailabilityResponse;
import com.klu.booking.dto.BlockDateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@FeignClient(name = "room-service")
public interface RoomServiceClient {

	@GetMapping("/rooms/{id}/availability")
	AvailabilityResponse checkAvailability(@PathVariable("id") Long roomId, @RequestParam("checkIn") LocalDate checkIn,
			@RequestParam("checkOut") LocalDate checkOut);

	@PostMapping("/rooms/block")
	Map<String, String> blockDates(@RequestBody BlockDateRequest request);

	@DeleteMapping("/rooms/release/{bookingId}")
	Map<String, String> releaseDates(@PathVariable("bookingId") Long bookingId);

}