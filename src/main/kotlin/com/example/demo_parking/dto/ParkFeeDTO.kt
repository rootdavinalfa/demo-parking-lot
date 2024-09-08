package com.example.demo_parking.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.math.BigDecimal
import java.time.LocalDateTime

data class ParkFeeDTO(
    val registrationNo: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.[SSS]")
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val arrival: LocalDateTime? = null,
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.[SSS]")
    val leave: LocalDateTime? = null,
    val totalBill: BigDecimal? = BigDecimal.ZERO,
    val elapsedHour: BigDecimal? = BigDecimal.ZERO,
)
