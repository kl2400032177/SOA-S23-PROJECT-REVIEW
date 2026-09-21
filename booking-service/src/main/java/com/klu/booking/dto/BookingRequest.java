package com.klu.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    @NotNull(message = "roomId is required")
    private Long roomId;

    @NotBlank(message = "guestEmail is required")
    @Email(message = "guestEmail must be valid")
    private String guestEmail;

    @NotNull(message = "checkIn is required")
    private LocalDate checkIn;

    @NotNull(message = "checkOut is required")
    private LocalDate checkOut;

}