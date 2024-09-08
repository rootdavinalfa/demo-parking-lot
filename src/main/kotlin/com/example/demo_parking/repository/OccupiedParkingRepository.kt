package com.example.demo_parking.repository

import com.example.demo_parking.entity.OccupiedParking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OccupiedParkingRepository : JpaRepository<OccupiedParking, Long>{

    @Query("SELECT COUNT(a) FROM OccupiedParking a " +
            "WHERE a.arrival is not null AND a.leave is not null")
    fun countOccupied(
    ): Long

}