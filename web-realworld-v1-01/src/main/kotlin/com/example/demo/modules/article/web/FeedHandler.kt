package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

//
// Feed記事一覧取得
// ログイン済みである
//
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/articles/feed' | jq '.'
@RestController
class FeedHandler {
    @GetMapping("/api/articles/feed")
    fun handle(@RequestBody nullableRawRequestBody: String?): String {
        return ObjectMapper()
            .writeValueAsString(
                ArticlesDto(
                    listOf(
                        ArticleDto(
                            "SpringBoot with Kotlinメモ",
                            "123456unique-slug",
                            "SpringBoot...",
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                            "概要",
                            listOf("tag1", "tag2"),
                            "John",
                            true,
                            999,
                        )
                    ),
                    1,
                )
            )
    }
}
