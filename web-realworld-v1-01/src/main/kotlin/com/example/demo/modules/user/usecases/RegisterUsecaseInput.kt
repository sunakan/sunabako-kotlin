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
    // エラー郡
    //
    data class ValidationErrors(override val errors: List<ErrorEvent>) : ErrorEvent.ErrorEvents
    sealed interface Error : ErrorEvent {
        object EmailMustBeNotNull : Error, ErrorEvent.Basic
        object PasswordMustBeNotNull : Error, ErrorEvent.Basic
        object UsernameMustBeNotNull : Error, ErrorEvent.Basic
    }
    companion object {
        fun new(
            email: String?,
            password: String?,
            username: String?,
        ): Either<ValidationErrors, RegisterUsecaseInput> {
            // この辺もっとうまく記述できる気がする
            if (email != null && password != null && username != null) {
                val a = RegisterUsecaseInputImpl(email, password, username)
                return Either.Right(a)
            }
            val errors: MutableList<ErrorEvent> = mutableListOf()
            if (email == null) { errors.add(Error.EmailMustBeNotNull) }
            if (password == null) { errors.add(Error.PasswordMustBeNotNull) }
            if (username == null) { errors.add(Error.UsernameMustBeNotNull) }
            return Either.Left(ValidationErrors(errors))
        }
    }

    private data class RegisterUsecaseInputImpl(
        override val email: String,
        override val password: String,
        override val username: String,
    ) : RegisterUsecaseInput
}
