package com.example.demo_parking.repository

import com.example.demo_parking.entity.OccupiedParking
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.Temporal
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OccupiedParkingRepository : JpaRepository<OccupiedParking, Long> {

    @Query(
        "SELECT COUNT(a) FROM OccupiedParking a " +
                "WHERE a.arrival is not null AND a.leave is not null"
    )
    fun countOccupied(
    ): Long


    @Query(
        "SELECT a FROM OccupiedParking a " +
                "WHERE a.arrival = :arrival AND a.registrationNo = :registrationNo"
    )
    fun findByRegistrationNoAndArrival(
        @Param("registrationNo") registrationNo: String?,
        @Param("arrival") arrival: LocalDateTime?
    ): OccupiedParking?


}