package com.example.demo.kotlin_practice.jackson_practice

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//
// https://www.baeldung.com/jackson-annotations#6-jsonrootname
//
// {
//   "name": "名無しの権兵衛",
//   "age": 999,
// }
// を "user" で1段階Wrapしたい
//
class SerializeExampleWithJsonRootName {
    @JsonRootName(value = "user") // <- Point
    data class DummyUser(
        @JsonProperty("name") val name: String,
        @JsonProperty("age") val age: Int,
    )

    @Test
    fun example() {
        val user = DummyUser("名無しの権兵衛", 999)
        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE) // <- Point
        val actual = mapper.writeValueAsString(user)
        val expected = "{\"user\":{\"name\":\"名無しの権兵衛\",\"age\":999}}"
        assertEquals(expected, actual)
    }
}
