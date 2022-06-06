package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

//
// ログイン済みであろうとなかろうと
// 記事一覧取得
//
// オプションとしてフィルタ可能
// - tag
// - author
// - favorited(ログイン済みのみ有効)
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/articles' | jq '.'
@RestController
class FilteredArticleListHandler {
    @GetMapping("/api/articles")
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
                            listOf("dragons", "training"),
                            "John",
                            true,
                            1,
                        )
                    ),
                    1,
                )
            )
    }
    // @JsonDeserialize(using = Deserializer ::class)
    // data class FilteredArticleListRequestBody(
    //    @JsonProperty("tag")       val tag: String? = null,
    //    @JsonProperty("author")    val author: String? = null,
    //    @JsonProperty("favorited") val favorited: String? = null,
    // )
}
