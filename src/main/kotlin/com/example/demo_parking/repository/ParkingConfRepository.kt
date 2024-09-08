package com.example.demo_parking.repository

import com.example.demo_parking.entity.ParkingConf
import org.springframework.data.jpa.repository.JpaRepository

interface ParkingConfRepository : JpaRepository<ParkingConf, Long>