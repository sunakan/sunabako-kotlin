package com.example.demo.kotlin_practice.jackson_practice

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

//
// https://www.baeldung.com/jackson-annotations#6-jsonrootname
//
// {
//   "title": "桃太郎"
// }
//
class SerializeExample {
    data class DummyBook(
        @JsonProperty("title") val title: String,
    )

    @Test
    fun example() {
        val book = DummyBook("桃太郎")
        val actual = ObjectMapper().writeValueAsString(book)
        val expected = "{\"title\":\"桃太郎\"}"
        Assertions.assertEquals(expected, actual)
    }
}
