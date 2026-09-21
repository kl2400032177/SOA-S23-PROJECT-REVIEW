package com.klu.room.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "blocked_dates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    // Reference to the booking that caused this block, so it can be released on cancellation
    @Column(nullable = false)
    private Long bookingId;

}