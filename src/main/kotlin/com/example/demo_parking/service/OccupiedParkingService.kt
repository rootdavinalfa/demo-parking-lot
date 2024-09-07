package com.example.demo_parking.service

import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.repository.OccupiedParkingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OccupiedParkingService
    (private var occupiedParkingRepository: OccupiedParkingRepository) {

    fun getPage(pageable: Pageable): Page<OccupiedParking> {
        return occupiedParkingRepository.findAll(pageable)
    }

}