package com.example.demo.modules.user.domains

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UnregisteredUserTest {
    private val okUnregisteredUser = UnregisteredUser.new(
        "john@example.com",
        "abcDEF123",
        "JohnSmith"
    )
    @Test
    fun `UnregisteredUserが生成できる`() {
        assertThat(okUnregisteredUser.isRight()).isTrue
    }

    //
    // @Provide
    // fun myPassword() : Arbitrary<String> = Arbitraries.strings().numeric().alpha().ofLength(8..64)
    // @Provide
    // fun myUsername() : Arbitrary<String> = Arbitraries.strings().numeric().alpha().ofLength(4..64)
    // @Property
    // fun `UnregisteredUserが生成できる`(
    //    @ForAll email: @Email String,
    //    @ForAll("myPassword") password: String,
    //    @ForAll("myUsername") username: String,
    // ) {
    //    val unregisteredUser = UnregisteredUser.new(email, password, username)
    //    when(unregisteredUser) {
    //        is Either.Right -> {}
    //        is Either.Left -> {
    //            println(unregisteredUser.value.errors)
    //        }
    //    }
    //    assertThat(unregisteredUser.isRight()).isTrue
    // }

    // @Property
    // fun `Emailが0文字は通らない不正の場合のバリデーションが通らずUnregisteredUserが生成できない`() {
    //    val ok = okUnregisteredUser.getOrElse { fail() } // ok が通ってる前提
    //    val ng = UnregisteredUser.new(
    //        "",
    //        ok.password,
    //        ok.username,
    //    )
    //    val errors = when (ng) {
    //        is Either.Right -> { fail() }
    //        is Either.Left -> { ng.value.errors }
    //    }
    //    assertTrue(errors.filterIsInstance(EmailValidationError::class.java).size == 1)
    // }
}
