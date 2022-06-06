package com.example.demo.modules.ops.web

import com.example.demo.shared.ErrorEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthHandler {
    @GetMapping("/api/health")
    fun handle(): ResponseEntity<String> {
        val ok = """{"message": "ok"}"""
        return ResponseEntity(ok, HttpStatus.BAD_REQUEST)
    }

    data class ValidationErrors(override val errors: List<ErrorEvent.BasicValidationError>) : ErrorEvent.ValidationErrors
    interface Error : ErrorEvent {
        data class EmailMustBeNotNull(override val key: String = "email", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
    }
}
