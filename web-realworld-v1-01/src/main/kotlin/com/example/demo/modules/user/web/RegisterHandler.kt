package com.example.demo.modules.user.web

import arrow.core.Either
import com.example.demo.modules.user.usecases.RegisterUsecaseInput
import com.example.demo.shared.ErrorEvent
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//
// 登録処理
//
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com", "password":"password", "username":"taro"}}' 'http://localhost:8080/api/users' | jq '.'
@RestController
class RegisterHandler {
    @PostMapping("/api/users")
    fun handle(@RequestBody nullableRawRequestBody: String?): ResponseEntity<String> {
        // when(NullableRawRequestBody2UnregisteredUser.convert(nullableRawRequestBody))
        val responseBody = ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
                UserDto(
                    "123@example.com",
                    "username-example",
                    "bio-example",
                    "image-example",
                    "token-example",
                )
            )
        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    // @JsonRootName(value = "user")
    // data class RegisterRequestBody(
    //    @JsonProperty("email")    val email: String,
    //    @JsonProperty("password") val password: String,
    //    @JsonProperty("username") val username: String,
    // )

    //
    // RequestBody(JSON) -> NullableRegisterUserDto -> UnregisteredUser
    //
    //
    private class NullableRawRequestBody2UnregisteredUser {
        sealed interface Error : ErrorEvent {
            val requestBody: String?
            data class FailedDeserialization(override val cause: JacksonException, override val requestBody: String?) : Error, ErrorEvent.BasicWithThrowable
            data class ValidationErrorForRegisterUsecaseInput(override val cause: ErrorEvent, override val requestBody: String?) : Error, ErrorEvent.BasicWithErrorEvent
            data class UnexpectedDefect(override val cause: Throwable, override val requestBody: String?) : Error, ErrorEvent.DefectWithThrowable
        }

        companion object {
            //
            // 変換関数
            //
            fun convert(requestBody: String?): Either<Error, RegisterUsecaseInput> {
                val data = try {
                    val nullableRegisterUserDto = ObjectMapper()
                        .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                        .readValue<NullableRegisterUserDto>(requestBody!!)
                    nullableRegisterUserDto
                } catch (cause: JacksonException) {
                    return Either.Left(Error.FailedDeserialization(cause, requestBody))
                } catch (cause: Throwable) {
                    return Either.Left(Error.UnexpectedDefect(cause, requestBody))
                }
                return RegisterUsecaseInput.new(data.email, data.password, data.username).fold(
                    { Either.Left(Error.ValidationErrorForRegisterUsecaseInput(it, requestBody)) },
                    { Either.Right(it) },
                )
            }
        }
    }
}
