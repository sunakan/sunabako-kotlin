package com.example.demo.modules.helloworld.web

import com.fasterxml.jackson.annotation.JsonProperty

data class HelloRequest(
    @JsonProperty("p-name") val name: String = "名前デフォルト値",
)
