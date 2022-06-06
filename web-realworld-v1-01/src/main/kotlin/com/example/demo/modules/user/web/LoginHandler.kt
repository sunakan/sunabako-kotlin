package com.example.demo.modules.user.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//
// ログイン処理
//
// $ curl -X POST --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com", "password":"password"}}' 'http://localhost:8080/api/users/login' | jq '.'
@RestController
class LoginHandler {
    @PostMapping("/api/users/login")
    fun handle(@RequestBody nullableRawRequestBody: String?): String {
        return ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
                UserDto(
                    "123@example.com",
                    "username-example",
                    "bio-example",
                    "image-example",
                    "token-example",
                )
            )
    }

    // @JsonRootName(value = "user")
    // data class LoginRequestBody(
    //    @JsonProperty("email")    val email: String,
    //    @JsonProperty("password") val password: String,
    // )
}
