package com.example.demo.modules.user.usecases

import arrow.core.Either
import com.example.demo.modules.user.domains.RegisteredUser
import com.example.demo.shared.ErrorEvent

interface RegisterUsecase {
    fun perform(): Either<Error, RegisteredUser>
    sealed class Error : ErrorEvent {
        class ValidationError(override val errors: List<ErrorEvent.BasicValidationError>) : Error(), ErrorEvent.ValidationErrors
        object DBError : Error(), ErrorEvent.Basic
    }
    // data class ValidationErrors(override val errors: List<ErrorEvent.BasicValidationError>) : ErrorEvent.ValidationErrors
    // sealed interface Error : ErrorEvent {
    //    // TODO: data classではなく、objectで表現できないか
    //    data class EmailMustBeNotNull(override val key: String = "email", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
    //    data class PasswordMustBeNotNull(override val key: String = "password", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
    //    data class UsernameMustBeNotNull(override val key: String = "username", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
    // }
}

// class RegisterUsecaseImpl(val params: RegisterUsecaseInput) : Either<Error, >
