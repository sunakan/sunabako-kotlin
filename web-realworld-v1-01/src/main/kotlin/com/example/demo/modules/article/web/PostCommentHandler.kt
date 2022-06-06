package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

//
// コメントを投稿
// ログイン済みである必要がある
//
// $ curl -X POST --header 'Content-Type: application/json' -d '{"article":{"title":"How to train your dragon", "description":"Ever wonder how?", "body":"Very carefully.", "tagList":["training", "dragons"]}}' 'http://localhost:8080/api/articles' | jq '.'
@RestController
class PostCommentHandler {
    @PostMapping("/api/articles/{slug}/comments")
    fun handle(@PathVariable("slug") nullableSlug: String): String {
        return ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
                CommentDto(
                    1,
                    "とても面白い記事でした",
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                    "名無しの権兵衛",
                )
            )
    }
}
