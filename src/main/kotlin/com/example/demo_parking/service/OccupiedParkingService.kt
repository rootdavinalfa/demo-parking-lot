package com.example.demo_parking.service

import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.error.ResourceExistsException
import com.example.demo_parking.error.ResourceNotFoundException
import com.example.demo_parking.repository.OccupiedParkingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class OccupiedParkingService(
    private var occupiedParkingRepository: OccupiedParkingRepository,
    private var parkingConfService: ParkingConfService
) {

    fun getPage(pageable: Pageable): Page<OccupiedParking> {
        return occupiedParkingRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun exists(id: Long): Boolean {
        return occupiedParkingRepository.existsById(id)
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    fun park(occupiedParking: OccupiedParking) {

        if (exists(occupiedParking.id)) {
            throw ResourceExistsException("Data is exists")
        }

        occupiedParking.arrival = LocalDateTime.now()
        val parkingConf =
            parkingConfService.findById(occupiedParking.confId!!)

        // Check if parking lot has available slot
        val occupiedCount = occupiedParkingRepository.countOccupied()
        if (occupiedCount > parkingConf.capacity) {
            throw ResourceExistsException("Parking lot is full, occupiedCount: $occupiedCount")
        }

        occupiedParking.firstHourRate = parkingConf.firstHourRate
        occupiedParking.incrementalHourRate = parkingConf.incrementalHourRate

        occupiedParkingRepository.save(occupiedParking)
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    fun leavePark(occupiedParking: OccupiedParking) {
        if (!exists(occupiedParking.id)) {
            throw ResourceNotFoundException("Data is not found")
        }

        occupiedParking.leave = LocalDateTime.now()
        val firstHourRate = occupiedParking.firstHourRate
        val incrementalHourRate = occupiedParking.incrementalHourRate
        val elapsedHour = occupiedParking.arrival?.until(occupiedParking.leave, ChronoUnit.HOURS) ?: 1L

        if (elapsedHour <= 1) {
            occupiedParking.totalBill = firstHourRate.multiply(elapsedHour.toBigDecimal())
        } else if (elapsedHour > 1) {
            occupiedParking.totalBill = firstHourRate.multiply(elapsedHour.toBigDecimal())
                .add(incrementalHourRate.multiply((elapsedHour - 1).toBigDecimal()))
        }

        occupiedParkingRepository.save(occupiedParking)

    }

}