package com.example.demo.kotlin_practice.pbt_jqwik_practice

//
// ユーザー
//
// 関数 require(value: Boolean)
// https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/require.html
data class PbtUser(val email: String) {
    init {
        val countAt = email.chars().filter { c: Int -> c == '@'.code }.count()
        require(countAt == 1L) { "emailは '@' は1個だけです" }
    }
}

//
// ノート
//
// Validation and DDD - kotlin data classes
// - https://stackoverflow.com/questions/52093474/validation-and-ddd-kotlin-data-classes
