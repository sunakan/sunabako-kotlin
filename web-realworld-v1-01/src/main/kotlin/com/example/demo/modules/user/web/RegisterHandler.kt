package com.example.demo.modules.user.web

import arrow.core.Either
import com.example.demo.modules.user.usecases.RegisterUsecase
import com.example.demo.modules.user.usecases.RegisterUsecaseImpl
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
import java.util.function.Function

//
// 登録処理
//
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com", "password":"Passw0rd", "username":"taro"}}' 'http://localhost:8080/api/users' | jq '.'
// or
// エラー例
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com"}}' 'http://localhost:8080/api/users' | jq '.'
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com", "password":"password", "username":"taro"}}' 'http://localhost:8080/api/users' | jq '.'
//
// String -> UsecaseInput
// UsecaseInput -> UsecaseOutput
//
@RestController
class RegisterHandler(
    val registerUsecase: RegisterUsecase = RegisterUsecaseImpl(),
    val requestBody2RegisterUsecaseInput: RequestBody2RegisterUsecaseInput = RequestBody2RegisterUsecaseInputImpl(),
) {
    @PostMapping("/api/users")
    fun handle(@RequestBody nullableRawRequestBody: String?): ResponseEntity<String> {
        // TODO: 早期リターンをやめるかも(for テスタブル with DI)
        val input = requestBody2RegisterUsecaseInput.apply(nullableRawRequestBody)
        val usecaseInput = when (input) {
            is Either.Left -> return when (val it = input.value) {
                is Error.InputError.ArgumentIsNull -> ResponseEntity("", HttpStatus.BAD_REQUEST)
                is Error.InputError.FailedDeserialization -> ResponseEntity("", HttpStatus.BAD_REQUEST)
                is Error.InputError.ValidationErrorForRegisterUsecaseInput -> {
                    val responseBody = ObjectMapper().writeValueAsString(mapOf("errors" to it.errors))
                    ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
                }
                is Error.InputError.UnexpectedDefect -> ResponseEntity("予期せぬエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR)
            }
            is Either.Right -> input.value
        }

        val usecaseResult = registerUsecase.apply(usecaseInput)
        val registeredUser = when (usecaseResult) {
            is Either.Left -> return when (val it = usecaseResult.value) {
                is RegisterUsecase.Error.UnregisteredUserValidationError -> {
                    val responseBody = ObjectMapper().writeValueAsString(mapOf("error" to it))
                    ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
                }
                is RegisterUsecase.Error.EmailIsAlreadyRegistered -> {
                    // TODO: 見せるときはバリデーションエラーとしたいため、UnregisteredUserValidationErrorと同じようなフォーマットにしたい
                    // なので、いい感じに詰め替え作業を行うやつが別で必要
                    // インフラ層とドメイン層ではエラー構造が異なる
                    //   => これを吸収する時、どちらも "知っている" やつが必要？
                    //     => それともinterfaceを用意して、それぞれに実装させるか?
                    //     => ドメイン層にinterfaceを用意して、インフラ層がそれに依存させるよう実装したらよいか
                    //
                    val responseBody = ObjectMapper().writeValueAsString(mapOf("error" to it))
                    ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
                }
                is RegisterUsecase.Error.FailedRegister -> ResponseEntity("予期せぬエラーが発生しました", HttpStatus.INTERNAL_SERVER_ERROR)
            }
            is Either.Right -> usecaseResult.value
        }

        // 詰め替え

        val responseBody = ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
                UserDto(
                    registeredUser.email,
                    registeredUser.username,
                    registeredUser.bio,
                    registeredUser.image,
                    "token-example",
                )
            )
        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    //
    // RequestBody(JSON) -> NullableRegisterUserDto -> UnregisteredUser
    //
    sealed interface Error : ErrorEvent {
        sealed interface InputError : Error {
            val requestBody: String?
            data class ArgumentIsNull(override val cause: NullPointerException, override val requestBody: String?) : InputError, ErrorEvent.BasicWithThrowable
            data class FailedDeserialization(override val cause: JacksonException, override val requestBody: String?) : InputError, ErrorEvent.BasicWithThrowable
            data class ValidationErrorForRegisterUsecaseInput(override val errors: List<ErrorEvent.BasicValidationError>, override val requestBody: String?) : InputError, ErrorEvent.ValidationErrors

            data class UnexpectedDefect(override val cause: Throwable, override val requestBody: String?) : InputError, ErrorEvent.DefectWithThrowable
        }
    }

    //
    // String -> RegisterUsecaseInput
    //
    interface RequestBody2RegisterUsecaseInput : Function<String?, Either<Error.InputError, RegisterUsecaseInput>>
    // @Component
    class RequestBody2RegisterUsecaseInputImpl : RequestBody2RegisterUsecaseInput {
        override fun apply(requestBody: String?): Either<Error.InputError, RegisterUsecaseInput> {
            val data = try {
                ObjectMapper().enable(DeserializationFeature.UNWRAP_ROOT_VALUE).readValue<NullableRegisterUserDto>(requestBody!!)
            } catch (cause: NullPointerException) {
                return Either.Left(Error.InputError.ArgumentIsNull(cause, requestBody))
            } catch (cause: JacksonException) {
                return Either.Left(Error.InputError.FailedDeserialization(cause, requestBody))
            } catch (cause: Throwable) {
                return Either.Left(Error.InputError.UnexpectedDefect(cause, requestBody))
            }
            return RegisterUsecaseInput.new(data.email, data.password, data.username).fold(
                { Either.Left(Error.InputError.ValidationErrorForRegisterUsecaseInput(it.errors, requestBody)) },
                { Either.Right(it) },
            )
        }
    }
}
