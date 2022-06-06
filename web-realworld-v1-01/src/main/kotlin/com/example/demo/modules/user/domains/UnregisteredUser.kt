package com.example.demo.modules.user.domains

import arrow.core.Either
import com.example.demo.shared.ErrorEvent

//
// 未登録ユーザー
//
interface UnregisteredUser {
    val email: String
    val password: String
    val username: String

    data class ValidationErrors(override val errors: List<ErrorEvent>) : ErrorEvent.ErrorEvents
    sealed interface Error : ErrorEvent {
        data class EmailValidationError(val email: String) : Error, ErrorEvent.Basic

        data class PasswordLengthError(val password: String) : Error, ErrorEvent.Basic
        data class PasswordMustContainAtLeastOneUppercase(val password: String) : Error, ErrorEvent.Basic
        data class PasswordMustContainAtLeastOneLowercase(val password: String) : Error, ErrorEvent.Basic
        data class PasswordMustContainAtLeastOneNumeric(val password: String) : Error, ErrorEvent.Basic

        data class UsernameLengthError(val username: String) : Error, ErrorEvent.Basic
        data class UsernameCharacterCombinationError(val username: String) : Error, ErrorEvent.Basic
    }

    companion object {
        fun new(
            email: String,
            password: String,
            username: String,
        ): Either<ValidationErrors, UnregisteredUser> {
            val errors: MutableList<ErrorEvent> = mutableListOf()
            // Email
            // https://android.googlesource.com/platform/frameworks/base/+/81aa097/core/java/android/util/Patterns.java#146
            val emailPattern = """[a-zA-Z0-9+._%\-+]{1,256}""" +
                """\@""" +
                """[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}""" +
                "(" +
                """\.""" +
                """[a-zA-Z0-9][a-zA-Z0-9\-]{0,25}""" +
                ")+"
            if (!email.matches(emailPattern.toRegex())) {
                errors.add(Error.EmailValidationError(email))
            }
            // Password
            if (password.length < 8 || 64 < password.length) {
                errors.add(Error.PasswordLengthError(password))
            }
            if (password.filter { it in 'A'..'Z' }.isEmpty()) {
                errors.add(Error.PasswordMustContainAtLeastOneUppercase(email))
            }
            if (password.filter { it in 'a'..'z' }.isEmpty()) {
                errors.add(Error.PasswordMustContainAtLeastOneLowercase(email))
            }
            if (password.filter { it in '0'..'9' }.isEmpty()) {
                errors.add(Error.PasswordMustContainAtLeastOneNumeric(email))
            }
            // Username
            if (username.length < 4 || 64 < username.length) {
                errors.add(Error.UsernameLengthError(username))
            }
            if (!username.matches("""\A[\w_\-]+\z""".toRegex())) {
                errors.add(Error.UsernameCharacterCombinationError(username))
            }

            return if (errors.isEmpty()) {
                Either.Right(UnregisteredUserImpl(email, password, username))
            } else {
                Either.Left(ValidationErrors(errors))
            }
        }
    }

    private data class UnregisteredUserImpl(
        override val email: String,
        override val password: String,
        override val username: String,
    ) : UnregisteredUser
}
