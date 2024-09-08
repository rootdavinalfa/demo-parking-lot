package com.example.demo_parking.dto

import jakarta.validation.constraints.NotNull

data class ParkDTO(
    @NotNull(message = "id must not be null")
    val id: Long? = null,
)
