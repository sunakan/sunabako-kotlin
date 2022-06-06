package com.example.demo.modules.user.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

//
// ログイン中のユーザー情報取得
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/user' | jq '.'
@RestController
class CurrentUserInfoHandler {
    @GetMapping("/api/user")
    fun handle(): String {
        // TODO
        // リクエストヘッダからAuthorization: Token ****
        // このTokenで探す
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
}
