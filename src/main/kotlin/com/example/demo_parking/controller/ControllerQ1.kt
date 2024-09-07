package com.example.demo_parking.controller

import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.service.OccupiedParkingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/q1")
@Tag(name = "Controller Q1")
class ControllerQ1
    (private var occupiedParkingService: OccupiedParkingService) {

    @GetMapping("/occupied/page")
    @Operation(summary = "Occupied Parking Page")
    fun occupiedParkingPage(
        @ParameterObject
        pageable: Pageable
    ): Page<OccupiedParking> {
        return occupiedParkingService.getPage(pageable)
    }


}