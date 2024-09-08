package com.example.demo_parking.service

import com.example.demo_parking.dto.ParkDTO
import com.example.demo_parking.dto.ParkFeeDTO
import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.error.ResourceExistsException
import com.example.demo_parking.error.ResourceNotFoundException
import com.example.demo_parking.repository.OccupiedParkingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
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

    fun findById(id: Long): OccupiedParking {
        return occupiedParkingRepository.findById(id)
            .orElseThrow { throw ResourceNotFoundException("Data not found") }
    }

    fun findByRegistrationNoAndArrival(registrationNo: String?, arrival: LocalDateTime?): OccupiedParking {
        return occupiedParkingRepository.findByRegistrationNoAndArrival(registrationNo, arrival)
            ?: throw ResourceNotFoundException("Data not found")
    }

    @Transactional(readOnly = true)
    fun exists(id: Long): Boolean {
        return occupiedParkingRepository.existsById(id)
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    fun park(occupiedParking: OccupiedParking): ParkDTO {

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
        return ParkDTO(
            occupiedParking.id
        )
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    fun leavePark(id: Long) : ParkFeeDTO {

        val occupiedParkingData = findById(
            id
        )

        occupiedParkingData.leave = LocalDateTime.now()
        val firstHourRate = occupiedParkingData.firstHourRate
        val incrementalHourRate = occupiedParkingData.incrementalHourRate
        val elapsedMinute = occupiedParkingData.arrival?.until(
            occupiedParkingData.leave,
            ChronoUnit.MINUTES
        ) ?: 60L

        val elapsedHour = BigDecimal(elapsedMinute.toDouble() / 60).setScale(0,RoundingMode.CEILING)

        if (elapsedHour <= BigDecimal(1)) {
            occupiedParkingData.totalBill = firstHourRate.multiply(BigDecimal(1))
        } else if (elapsedHour > BigDecimal(1)) {
            occupiedParkingData.totalBill = firstHourRate.multiply(BigDecimal(1))
                .add(incrementalHourRate.multiply((elapsedHour.subtract(BigDecimal(1)))))
        }

        occupiedParkingRepository.save(occupiedParkingData)

        return ParkFeeDTO(
            occupiedParkingData.registrationNo,
            occupiedParkingData.arrival,
            occupiedParkingData.leave,
            occupiedParkingData.totalBill,
            elapsedHour
        )

    }

}