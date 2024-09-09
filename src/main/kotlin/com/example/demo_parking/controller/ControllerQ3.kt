package com.example.demo_parking.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Controller Q3")
class ControllerQ3 {

    @GetMapping("/")
    @Operation(summary = "Question 3")
    fun getData(): Int? {
        val lists = listOf(8, 1, 6, 3, 66, 1, 8, 5)
        val x = 3

        val windowed = lists.windowed(x)
        val min = windowed.map {
            it.minOrNull() ?: 0
        }

        return min.maxOrNull()
    }

}