package com.example.demo_parking.controller

import com.example.demo_parking.dto.ParkDTO
import com.example.demo_parking.dto.ParkFeeDTO
import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.entity.ParkingConf
import com.example.demo_parking.service.OccupiedParkingService
import com.example.demo_parking.service.ParkingConfService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/q1")
@Tag(name = "Controller Q1")
class ControllerQ1(
    private var occupiedParkingService: OccupiedParkingService,
    private var parkingConfService: ParkingConfService
) {

    @GetMapping("/parking/page")
    @Operation(summary = "Parking Page")
    fun occupiedParkingPage(
        @ParameterObject
        pageable: Pageable
    ): Page<OccupiedParking> {
        return occupiedParkingService.getPage(pageable)
    }

    @PostMapping("/config")
    @Operation(summary = "Create configuration")
    fun createConfiguration(
        @Validated
        @RequestBody configuration: ParkingConf
    ): ParkingConf {
        return parkingConfService.save(configuration)
    }

    @PutMapping("/config")
    @Operation(summary = "Update configuration")
    fun updateConfiguration(
        @Validated
        @RequestBody configuration: ParkingConf
    ): ParkingConf {
        return parkingConfService.update(configuration)
    }

    @GetMapping("/config/{id}")
    @Operation(summary = "Get configuration")
    fun getConfiguration(@PathVariable id: Long): ParkingConf {
        return parkingConfService.findById(id)
    }

    @PostMapping("/parking/arrive")
    @Operation(summary = "Parking action arrive")
    fun parking(
        @RequestBody
        @Validated
        occupiedParking: OccupiedParking
    ): ParkDTO {
        return occupiedParkingService.park(occupiedParking)
    }

    @PutMapping("/parking/leave")
    @Operation(summary = "Parking action leave")
    fun leave(
        @RequestBody
        @Validated
        parkDTO: ParkDTO
    ): ParkFeeDTO {
        return occupiedParkingService.leavePark(parkDTO.id ?: -1)
    }


}