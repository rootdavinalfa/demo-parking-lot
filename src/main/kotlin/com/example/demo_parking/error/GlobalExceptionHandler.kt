package com.example.demo_parking.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.stream.Collectors


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<*> {
        val errors = ex.bindingResult.fieldErrors
            .stream().map { obj: FieldError -> obj.defaultMessage }.collect(Collectors.toList())
        println(errors.toString())
        return ResponseEntity<Any?>(
            ErrorResponse(errors.toString(), ex.cause, request.getDescription(false)),
            HttpStatus.BAD_REQUEST
        )
    }


    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ResourceExistsException::class)
    fun resourceExistException(ex: ResourceExistsException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun resourceExistException(ex: Exception, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }


}