package com.example.demo_parking.error

data class ErrorResponse(
    val message: String?,
    val cause: Throwable?,
    val url: String?
)
