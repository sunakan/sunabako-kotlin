package com.example.demo.modules.user.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

//
// ユーザーEmail更新
//
// $ curl -X PUT --header 'Content-Type: application/json' -d '{"user":{"email":"1234@example.com"}}' 'http://localhost:8080/api/user' | jq '.'
@RestController
class UpdateEmailHandler {
    @PutMapping("/api/user")
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
    // data class UpdateEmailRequestBody(
    //    @JsonProperty("email")    val email: String,
    // )
}
