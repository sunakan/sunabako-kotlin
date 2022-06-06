package com.example.demo.modules.article.web

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

//
// 対象の記事を削除
// - ログイン済みである
//
// $ curl -X DELETE --header 'Content-Type: application/json' 'http://localhost:8080/api/articles/123456unique-slug/favorite' | jq '.'
@RestController
class DeleteArticleHandler {
    @DeleteMapping("/api/articles/{slug}")
    fun handle(@PathVariable("slug") slug: String): String {
        return ""
    }
}
