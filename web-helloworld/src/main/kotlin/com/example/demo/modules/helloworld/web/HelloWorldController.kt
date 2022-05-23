package com.example.demo.modules.helloworld.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    //
    // Hello1
    //
    // $ curl --silent http://localhost:8080/hello1 | jq '.'
    //
    @GetMapping("/hello1")
    fun hello1(): String {
        return """
            {
                "message": "HelloWorld"
            }
        """.trimIndent()
    }

    //
    // Hello2
    // リクエストパラメータの受け取り方
    //
    // $ curl --silent 'http://localhost:8080/hello2?p-name=Takahashi' | jq '.'
    //
    @GetMapping("/hello2")
    fun hello2(@RequestParam(value = "p-name", defaultValue = "佐藤") name: String): String {
        return """
            {
                "message": "$name"
            }
        """.trimIndent()
    }

    //
    // Hello3
    // URLの動的なパラメータを受け取る
    //
    // $ curl --silent 'http://localhost:8080/hello3/Tanaka' | jq '.'
    //
    @GetMapping("/hello3/{x-name}")
    fun hello3(@PathVariable("x-name") name: String): String {
        return """
            {
                "message": "$name"
            }
        """.trimIndent()
    }

    //
    // Hello4
    // - Content-Typeにapplication/jsonを指定して、リクエストボディ
    // - レスポンスをクラスで表現
    //
    // 無指定
    // $ curl --silent -X GET -H 'Content-Type: application/json' -d '{}' 'http://localhost:8080/hello4' | jq '.'
    // 指定
    // $ curl --silent -X GET -H 'Content-Type: application/json' -d '{"p-name": "Sato"}' 'http://localhost:8080/hello4' | jq '.'
    // 存在しなやつは無視される
    // $ curl --silent -X GET -H 'Content-Type: application/json' -d '{"p-name": "Sato", "foo": "bar"}' 'http://localhost:8080/hello4' | jq '.'
    //
    @GetMapping("/hello4")
    fun hello4(@RequestBody request: HelloRequest): HelloResponse {
        return createHelloResponse(request.name)
    }
}
