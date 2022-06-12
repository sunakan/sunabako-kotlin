package com.example.demo.modules.user.usecases

import arrow.core.Either
import com.example.demo.modules.user.domains.RegisteredUser
import com.example.demo.modules.user.domains.UnregisteredUser
import com.example.demo.modules.user.repository.RegisterUserCommand
import com.example.demo.modules.user.repository.RegisterUserCommandImpl
import com.example.demo.shared.ErrorEvent
import java.util.function.Function

//
// ユーザー登録
//
interface RegisterUsecase : Function<RegisterUsecaseInput, Either<RegisterUsecase.Error, RegisteredUser>> {
    sealed class Error : ErrorEvent {
        data class UnregisteredUserValidationError(override val cause: ErrorEvent, val input: RegisterUsecaseInput) : Error(), ErrorEvent.BasicWithErrorEvent
        data class EmailIsAlreadyRegistered(override val cause: ErrorEvent, val input: RegisterUsecaseInput) : Error(), ErrorEvent.BasicWithErrorEvent
        data class FailedRegister(override val cause: ErrorEvent, val input: RegisterUsecaseInput) : Error(), ErrorEvent.BasicWithErrorEvent
        // TODO: Failureにすべきかも
    }
}

class RegisterUsecaseImpl(private val registerUserCommand: RegisterUserCommand = RegisterUserCommandImpl()) : RegisterUsecase {
    override fun apply(input: RegisterUsecaseInput): Either<RegisterUsecase.Error, RegisteredUser> {
        val newUnregisteredUser = UnregisteredUser.new(input.email, input.password, input.username)
        val unregisteredUser = when (newUnregisteredUser) {
            is Either.Left -> return Either.Left(RegisterUsecase.Error.UnregisteredUserValidationError(newUnregisteredUser.value, input))
            is Either.Right -> newUnregisteredUser.value
        }
        val registerUserCommandResult = registerUserCommand.apply(unregisteredUser)
        val commandError2UsecaseError = object: Function<RegisterUserCommand.Error, RegisterUsecase.Error> {
            override fun apply(it: RegisterUserCommand.Error): RegisterUsecase.Error {
                return when (it) {
                    is RegisterUserCommand.Error.DuplicateEmailError -> RegisterUsecase.Error.EmailIsAlreadyRegistered(it, input)
                    else -> RegisterUsecase.Error.FailedRegister(it, input)
                }
            }
        }
        return registerUserCommandResult.fold(
            { Either.Left(commandError2UsecaseError.apply(it)) },
            { Either.Right(it) }
        )
    }
}
