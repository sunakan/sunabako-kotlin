package com.example.demo.modules.user.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

//
// (セレブ/有名な)ユーザー情報の取得
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/profiles/celeb_tanaka' | jq '.'
@RestController
class FollowCelebUserHandler {
    @PostMapping("/api/profiles/celeb_{username}/follow")
    fun handle(@PathVariable("username") userName: String): String {
        return ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
                CelebProfileDto(
                    "HIKAKIN",
                    "bio",
                    "image-example",
                    true,
                )
            )
    }
}
