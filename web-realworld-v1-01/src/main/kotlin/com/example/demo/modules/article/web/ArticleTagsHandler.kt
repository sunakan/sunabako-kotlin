package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

//
// 1つの記事取得
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/articles/123456unique-slug' | jq '.'
@RestController
class ArticleTagsHandler {
    @GetMapping("/api/tags")
    fun handle(): String {
        return ObjectMapper()
            .writeValueAsString(
                ArticleTagsDto(
                    listOf()
                )
            )
    }
}
