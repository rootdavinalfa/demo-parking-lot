package com.example.demo_parking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
class DemoParkingApplication

fun main(args: Array<String>) {
    runApplication<DemoParkingApplication>(*args)
}
