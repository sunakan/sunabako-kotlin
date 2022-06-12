package com.example.demo.modules.user.repository

import arrow.core.Either
import com.example.demo.Dbconfiguration
import com.example.demo.modules.user.domains.RegisteredUser
import com.example.demo.modules.user.domains.UnregisteredUser
import com.example.demo.shared.ErrorEvent
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.CannotGetJdbcConnectionException
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.transaction.TransactionManager
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.function.Function

//
// Repositoryではなく、一旦CommandQueryでやってみる
// TODO: Repositoryに抽象化して、再利用可能にする
interface RegisterUserCommand : Function<UnregisteredUser, Either<RegisterUserCommand.Error, RegisteredUser>> {
    sealed interface Error : ErrorEvent {
        data class DuplicateEmailError(override val cause: Throwable, val unregisteredUser: UnregisteredUser) : Error, ErrorEvent.BasicWithThrowable
        data class DbError(override val cause: Throwable, val unregisteredUser: UnregisteredUser) : Error, ErrorEvent.BasicWithThrowable
        data class ObjectMappingError(override val cause: Throwable, val unregisteredUser: UnregisteredUser) : Error, ErrorEvent.BasicWithThrowable
        data class UnexpectedError(override val cause: Throwable, val unregisteredUser: UnregisteredUser) : Error, ErrorEvent.BasicWithThrowable
    }
}

// TODO: idをアプリ側で自作(UULD)?
class RegisterUserCommandImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate = Dbconfiguration.namedParameterJdbcTemplate(),
    private val transactionManager: TransactionManager = Dbconfiguration.transactionManager(),
) : RegisterUserCommand {
    val sql1 = "INSERT INTO users(email, username, password, created_at, updated_at) VALUES (:email, :username, :password, :created_at, :updated_at) RETURNING id;"
    val sql2 = "INSERT INTO profiles(user_id, bio, image, created_at, updated_at) VALUES (:user_id, :bio, :image, :created_at, :updated_at);"
    override fun apply(unregisteredUser: UnregisteredUser): Either<RegisterUserCommand.Error, RegisteredUser> {
        val currentTimestamp = OffsetDateTime.now(ZoneOffset.UTC)
        val sqlParams1 = MapSqlParameterSource()
            .addValue("email", unregisteredUser.email)
            .addValue("username", unregisteredUser.username)
            .addValue("password", unregisteredUser.password)
            .addValue("created_at", currentTimestamp)
            .addValue("updated_at", currentTimestamp)
        val map = try {
            jdbcTemplate.queryForMap(sql1, sqlParams1)
        } catch (e: DuplicateKeyException) { // 一意制約違反が発生した場合に発生する例外。
            return Either.Left(RegisterUserCommand.Error.DuplicateEmailError(e, unregisteredUser))
        } catch (e: CannotGetJdbcConnectionException) { // JDBC を使用して RDBMS に接続できない場合にスローされる致命的な例外
            return Either.Left(RegisterUserCommand.Error.DbError(e, unregisteredUser))
        } catch (e: Throwable) { // 基本的に想定外、catchの度にエラーを分析し、エラーを追加していく
            return Either.Left(RegisterUserCommand.Error.UnexpectedError(e, unregisteredUser))
        }
        val userId = try {
            map["id"] as Int
        } catch (e: NullPointerException) {
            return Either.Left(RegisterUserCommand.Error.ObjectMappingError(e, unregisteredUser))
        } catch (e: java.lang.ClassCastException) {
            return Either.Left(RegisterUserCommand.Error.ObjectMappingError(e, unregisteredUser))
        } catch (e: Throwable) { // 基本的に想定外、catchの度にエラーを分析し、エラーを追加していく
            return Either.Left(RegisterUserCommand.Error.UnexpectedError(e, unregisteredUser))
        }
        val bio = "registered-user-bio"
        val image = "registered-user-img"
        val sqlParams2 = MapSqlParameterSource()
            .addValue("user_id", userId)
            .addValue("bio", bio)
            .addValue("image", image)
            .addValue("created_at", currentTimestamp)
            .addValue("updated_at", currentTimestamp)
        try {
            jdbcTemplate.update(sql2, sqlParams2)
        } catch (e: CannotGetJdbcConnectionException) { // JDBC を使用して RDBMS に接続できない場合にスローされる致命的な例外
            return Either.Left(RegisterUserCommand.Error.DbError(e, unregisteredUser))
        } catch (e: Throwable) { // 基本的に想定外、catchの度にエラーを分析し、エラーを追加していく
            return Either.Left(RegisterUserCommand.Error.UnexpectedError(e, unregisteredUser))
        }

        return Either.Right(RegisteredUser(
            userId,
            unregisteredUser.email,
            unregisteredUser.username,
            bio,
            image,
        ))
    }
}