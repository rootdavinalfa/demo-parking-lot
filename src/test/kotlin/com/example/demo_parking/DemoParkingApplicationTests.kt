package com.example.demo_parking

import com.example.demo_parking.dto.ParkDTO
import com.example.demo_parking.entity.OccupiedParking
import com.example.demo_parking.entity.ParkingConf
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoParkingApplicationTests {

    @Autowired
    private lateinit var webClient: WebTestClient
    private var parkingConfCarId: Long = 0
    private var parkingConfTruckId: Long = 0
    private var parkingCar: ParkDTO? = null
    private var parkingTruck: ParkDTO? = null

    @BeforeAll
    fun createParkingConf() {
        val parkingConf = ParkingConf()
        parkingConf.name = "Test1"
        parkingConf.capacity = 10
        parkingConf.firstHourRate = BigDecimal(3_000)
        parkingConf.incrementalHourRate = BigDecimal(2_000)
        val parkingConfResult = webClient.post().uri("/q1/config").bodyValue(
            parkingConf
        ).exchange()
            .expectStatus().isOk
            .returnResult<ParkingConf>()
        parkingConfCarId = parkingConfResult.responseBody.blockLast()?.id ?: -1
    }

    @BeforeAll
    fun createParkingConfTruck() {
        val parkingConf = ParkingConf()
        parkingConf.name = "Truck"
        parkingConf.capacity = 10
        parkingConf.firstHourRate = BigDecimal(8_000)
        parkingConf.incrementalHourRate = BigDecimal(4_000)
        val parkingConfResult = webClient.post().uri("/q1/config").bodyValue(
            parkingConf
        ).exchange()
            .expectStatus().isOk
            .returnResult<ParkingConf>()
        parkingConfTruckId = parkingConfResult.responseBody.blockLast()?.id ?: -1
    }

    @Test
    @Order(1)
    fun updateParkingConfToCar() {
        val parkingConf = ParkingConf()
        parkingConf.id = parkingConfCarId
        parkingConf.name = "Car"
        parkingConf.capacity = 100
        parkingConf.firstHourRate = BigDecimal(5_000)
        parkingConf.incrementalHourRate = BigDecimal(3_000)
        webClient.put().uri("/q1/config").bodyValue(
            parkingConf
        ).exchange()
            .expectStatus().isOk
    }

    @Test
    @Order(3)
    fun parkCar() {
        val occupiedParking = OccupiedParking()
        occupiedParking.registrationNo = "B 2077 XYZ"
        occupiedParking.confId = parkingConfCarId
        val parkCarResult = webClient.post().uri("/q1/parking/arrive").bodyValue(
            occupiedParking
        ).exchange()
            .expectStatus().isOk
            .returnResult<ParkDTO>()
        parkingCar = parkCarResult.responseBody.blockLast()
    }

    @Test
    @Order(4)
    fun parkTruck() {
        val occupiedParking = OccupiedParking()
        occupiedParking.registrationNo = "K 3389 FK"
        occupiedParking.confId = parkingConfTruckId
        val parkTruckResult = webClient.post().uri("/q1/parking/arrive").bodyValue(
            occupiedParking
        ).exchange()
            .expectStatus().isOk
            .returnResult<ParkDTO>()
        parkingTruck = parkTruckResult.responseBody.blockLast()

    }

    @Test
    @Order(5)
    fun unParkCar() {
        webClient.put().uri("/q1/parking/leave").bodyValue(
            parkingCar ?: ParkDTO()
        ).exchange()
            .expectStatus().isOk
    }

    @Test
    @Order(6)
    fun unParkTruck() {
        webClient.put().uri("/q1/parking/leave").bodyValue(
            parkingTruck ?: ParkDTO()
        ).exchange()
            .expectStatus().isOk
    }

}
