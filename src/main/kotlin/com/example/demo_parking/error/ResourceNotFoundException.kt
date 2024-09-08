package com.example.demo_parking.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class ResourceNotFoundException(override val message: String?) : RuntimeException()
