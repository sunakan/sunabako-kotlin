package com.example.demo.modules.user.domains

//
// 登録済みのユーザー
//
// AccessTokenを持っていないのは意図的
//
data class RegisteredUser(
    val id: Int,
    val email: String,
    val username: String,
    val bio: String,
    val image: String,
)
