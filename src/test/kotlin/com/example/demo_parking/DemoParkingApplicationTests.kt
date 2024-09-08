package com.example.demo_parking

import com.example.demo_parking.entity.ParkingConf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoParkingApplicationTests {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun testCases() {
        val parkingConf = ParkingConf()
        parkingConf.name = "Test1"
        parkingConf.capacity = 10
        parkingConf.firstHourRate = BigDecimal(3_000)
        parkingConf.incrementalHourRate = BigDecimal(2_000)
        webClient.post().uri("/q1/config").bodyValue(
            parkingConf
        ).exchange()
            .expectStatus().isOk
    }

}
