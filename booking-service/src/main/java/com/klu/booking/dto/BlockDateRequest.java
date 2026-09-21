package com.klu.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockDateRequest {

    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long bookingId;

}