package com.example.demo.modules.article.web

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticlesDto(
    @JsonProperty("articles") val articles: List<ArticleDto>,
    @JsonProperty("articlesCount") val articlesCount: Int,
)
