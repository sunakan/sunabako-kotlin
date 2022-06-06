package com.example.demo.modules.article.web

import com.fasterxml.jackson.annotation.JsonProperty

data class CommentsDto(
    @JsonProperty("comments") val comments: List<CommentDto>,
)
