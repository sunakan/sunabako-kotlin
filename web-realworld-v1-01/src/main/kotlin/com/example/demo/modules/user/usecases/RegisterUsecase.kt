package com.example.demo.modules.user.usecases

import arrow.core.Either
import com.example.demo.modules.user.domains.RegisteredUser
import com.example.demo.shared.ErrorEvent

interface RegisterUsecase {
    fun perform(): Either<Error, RegisteredUser>
    sealed class Error : ErrorEvent {
        object ValidationError : Error(), ErrorEvent.Basic
        object DBError : Error(), ErrorEvent.Basic
    }
}

class RegisterUsecaseImpl(val params: RegisterUsecaseInput)
