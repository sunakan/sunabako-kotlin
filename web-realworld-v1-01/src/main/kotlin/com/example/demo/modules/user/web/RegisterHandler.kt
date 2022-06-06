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
// or
// エラー例
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com"}}' 'http://localhost:8080/api/users' | jq '.'
@RestController
class RegisterHandler {
    @PostMapping("/api/users")
    fun handle(@RequestBody nullableRawRequestBody: String?): ResponseEntity<String> {
        // TODO: 早期リターンをやめるかも(for テスタブル with DI)
        val usecaseInput = when (val input = convert(nullableRawRequestBody)) {
            is Either.Left -> return when (val it = input.value) {
                is Error.FailedDeserialization -> {
                    ResponseEntity("", HttpStatus.BAD_REQUEST)
                }
                is Error.ValidationErrorForRegisterUsecaseInput -> {
                    val responseBody = ObjectMapper().writeValueAsString(it.cause)
                    ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
                }
                is Error.UnexpectedDefect -> {
                    // Slack Alert(it)
                    ResponseEntity("予期せぬエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR)
                }
            }
            is Either.Right -> input.value
        }

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

    //
    // RequestBody(JSON) -> NullableRegisterUserDto -> UnregisteredUser
    //
    sealed interface Error : ErrorEvent {
        val requestBody: String?
        data class FailedDeserialization(override val cause: JacksonException, override val requestBody: String?) : Error, ErrorEvent.BasicWithThrowable
        data class ValidationErrorForRegisterUsecaseInput(override val cause: RegisterUsecaseInput.ValidationErrors, override val requestBody: String?) : Error, ErrorEvent.BasicWithErrorEvent
        // 謎エラー
        data class UnexpectedDefect(override val cause: Throwable, override val requestBody: String?) : Error, ErrorEvent.DefectWithThrowable
    }

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

// interface Json2UsecaseInput {
//    fun convert(json: String?): Either<Error, RegisterUsecaseInput>
//    sealed interface Error : ErrorEvent {
//        val requestBody: String?
//        data class FailedDeserialization(override val cause: JacksonException, override val requestBody: String?) : RegisterHandler.Error, ErrorEvent.BasicWithThrowable
//        data class ValidationErrorForRegisterUsecaseInput(override val cause: RegisterUsecaseInput.ValidationErrors, override val requestBody: String?) : RegisterHandler.Error, ErrorEvent.BasicWithErrorEvent
//        data class UnexpectedDefect(override val cause: Throwable, override val requestBody: String?) : RegisterHandler.Error, ErrorEvent.DefectWithThrowable
//    }
// }
// @Component
// class Json2UsecaseInputImpl {
// }
