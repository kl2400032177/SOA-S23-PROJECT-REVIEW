package com.klu.room.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BlockDateRequest {

    @NotNull(message = "roomId is required")
    private Long roomId;

    @NotNull(message = "checkIn is required")
    private LocalDate checkIn;

    @NotNull(message = "checkOut is required")
    private LocalDate checkOut;

    @NotNull(message = "bookingId is required")
    private Long bookingId;

}