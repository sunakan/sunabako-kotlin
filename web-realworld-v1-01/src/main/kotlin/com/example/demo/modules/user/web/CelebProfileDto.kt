package com.example.demo.modules.user.web

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName(value = "profile")
data class CelebProfileDto(
    @JsonProperty("username") val username: String,
    @JsonProperty("bio") val bio: String,
    @JsonProperty("image") val image: String,
    @JsonProperty("following") val following: Boolean,
)
