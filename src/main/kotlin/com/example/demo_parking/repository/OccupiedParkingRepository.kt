package com.example.demo_parking.repository

import com.example.demo_parking.entity.OccupiedParking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OccupiedParkingRepository : JpaRepository<OccupiedParking, Long>