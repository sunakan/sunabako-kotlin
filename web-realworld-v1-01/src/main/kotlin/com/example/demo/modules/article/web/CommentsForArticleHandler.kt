package com.example.demo.modules.article.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat

//
// 対象の記事のコメント一覧取得
//
// $ curl -X GET --header 'Content-Type: application/json' 'http://localhost:8080/api/articles' | jq '.'
@RestController
class CommentsForArticleHandler {
    @GetMapping("/api/articles/{slug}/comments")
    fun handle(@PathVariable("slug") slug: String): String {
        return ObjectMapper()
            .writeValueAsString(
                CommentsDto(
                    listOf(
                        CommentDto(
                            1,
                            "とても面白い記事でした",
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2022-01-01T01:23:45+09:00"),
                            "名無しの権兵衛",
                        )
                    ),
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
