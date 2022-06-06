package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

//
// 対象の記事を更新
// - ログイン済みである
//
// $ curl -X POST --header 'Content-Type: application/json' 'http://localhost:8080/api/articles/123456unique-slug/favorite' | jq '.'
@RestController
class FavoriteArticleHandler {
    @PostMapping("/api/articles/{slug}/favorite")
    fun handle(@PathVariable("slug") nullableSlug: String): String {
        return ObjectMapper()
            .enable(SerializationFeature.WRAP_ROOT_VALUE)
            .writeValueAsString(
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
                    1,
                )
            )
    }
}
