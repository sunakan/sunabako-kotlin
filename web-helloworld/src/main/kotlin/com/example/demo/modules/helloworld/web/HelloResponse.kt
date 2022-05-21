package com.example.demo.modules.helloworld.web

// Response例
// {
//   nest: {
//     message: "***"
//   }
// }
data class HelloResponse(
    val nest: Message
)
data class Message(
    val message: String,
)

fun createHelloResponse(
    message: String,
): HelloResponse {
    return HelloResponse(
        Message(message)
    )
}
