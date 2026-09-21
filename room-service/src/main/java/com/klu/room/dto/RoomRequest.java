package com.klu.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {

    @NotNull(message = "propertyId is required")
    private Long propertyId;

    @NotBlank(message = "propertyName is required")
    private String propertyName;

    @NotBlank(message = "roomType is required")
    private String roomType;

    @NotNull(message = "pricePerNight is required")
    @Positive(message = "pricePerNight must be positive")
    private Double pricePerNight;

    @NotNull(message = "capacity is required")
    @Positive(message = "capacity must be positive")
    private Integer capacity;

}