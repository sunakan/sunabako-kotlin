package com.example.demo.modules.user.web

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonIgnoreProperties(ignoreUnknown = true) // ここにないプロパティがあった時、それを無視する
@JsonRootName(value = "user")
data class NullableRegisterUserDto(
    @JsonProperty("email") val email: String?,
    @JsonProperty("password") val password: String?,
    @JsonProperty("username") val username: String?,
    @JsonProperty("bio") val bio: String?,
    @JsonProperty("image") val image: String?,
)
