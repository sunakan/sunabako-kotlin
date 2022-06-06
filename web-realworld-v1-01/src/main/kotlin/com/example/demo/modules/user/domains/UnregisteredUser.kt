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
        data class EmailValidationError(val email: String, override val key: String = "email", override val message: String = "emailアドレスを入力してください(例: abc@example.com)") : Error, ErrorEvent.BasicValidationError
        data class PasswordLengthError(override val key: String = "password", override val message: String = "passwordは8文字以上64文字以下である必要があります") : Error, ErrorEvent.BasicValidationError
        data class PasswordMustContainAtLeastOneUppercase(override val key: String = "password", override val message: String = "passwordには大文字の英字を1文字以上含めてください") : Error, ErrorEvent.BasicValidationError
        data class PasswordMustContainAtLeastOneLowercase(override val key: String = "password", override val message: String = "passwordには小文字の英字を1文字以上含めてください") : Error, ErrorEvent.BasicValidationError
        data class PasswordMustContainAtLeastOneNumeric(override val key: String = "password", override val message: String = "passwordには半角数字を1文字以上含めてください") : Error, ErrorEvent.BasicValidationError
        data class UsernameLengthError(val username: String, override val key: String = "username", override val message: String = "usernameは4文字以上64文字以下である必要があります") : Error, ErrorEvent.BasicValidationError
        data class UsernameCharacterCombinationError(val username: String, override val key: String = "username", override val message: String = "usernameに使えるのは半角英数字と_です") : Error, ErrorEvent.BasicValidationError
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
                errors.add(Error.PasswordLengthError())
            }
            if (password.none { it in 'A'..'Z' }) {
                errors.add(Error.PasswordMustContainAtLeastOneUppercase())
            }
            if (password.none { it in 'a'..'z' }) {
                errors.add(Error.PasswordMustContainAtLeastOneLowercase())
            }
            if (password.none { it in '0'..'9' }) {
                errors.add(Error.PasswordMustContainAtLeastOneNumeric())
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
