package com.example.demo.modules.user.usecases

import arrow.core.Either
import com.example.demo.shared.ErrorEvent

//
// ユーザー登録ユースケース用のInput
//
interface RegisterUsecaseInput {
    val email: String
    val password: String
    val username: String

    //
    // エラー
    //
    data class ValidationErrors(override val errors: List<ErrorEvent.BasicValidationError>) : ErrorEvent.ValidationErrors
    sealed interface Error : ErrorEvent {
        // TODO: data classではなく、objectで表現できないか
        data class EmailMustBeNotNull(override val key: String = "email", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
        data class PasswordMustBeNotNull(override val key: String = "password", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
        data class UsernameMustBeNotNull(override val key: String = "username", override val message: String = "必須項目です") : Error, ErrorEvent.BasicValidationError
    }
    companion object {
        fun new(
            email: String?,
            password: String?,
            username: String?,
        ): Either<ValidationErrors, RegisterUsecaseInput> {
            // TODO: もっとうまく記述できる気がする
            if (email != null && password != null && username != null) {
                return Either.Right(RegisterUsecaseInputImpl(email, password, username))
            }
            val errors: MutableList<ErrorEvent.BasicValidationError> = mutableListOf()
            if (email == null) { errors.add(Error.EmailMustBeNotNull()) }
            if (password == null) { errors.add(Error.PasswordMustBeNotNull()) }
            if (username == null) { errors.add(Error.UsernameMustBeNotNull()) }
            return Either.Left(ValidationErrors(errors))
        }
    }

    private data class RegisterUsecaseInputImpl(
        override val email: String,
        override val password: String,
        override val username: String,
    ) : RegisterUsecaseInput
}
