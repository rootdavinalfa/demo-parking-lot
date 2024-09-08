package com.example.demo_parking.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
data class ResourceExistsException(
    override val message: String?
) : RuntimeException()
