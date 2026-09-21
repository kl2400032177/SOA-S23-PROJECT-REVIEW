package com.klu.room.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long propertyId;

    @Column(nullable = false)
    private String propertyName;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private Double pricePerNight;

    @Column(nullable = false)
    private Integer capacity;

}