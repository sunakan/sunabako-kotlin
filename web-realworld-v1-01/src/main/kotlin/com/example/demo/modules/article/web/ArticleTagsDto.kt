package com.example.demo.modules.article.web

import com.fasterxml.jackson.annotation.JsonProperty

data class ArticleTagsDto(
    @JsonProperty("tags") val tags: List<String>,
)
