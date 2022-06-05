package demo.tutorial02

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertEquals

//
// Serialize(Object -> JSON 文字列)
// https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
//
// List<User> の to JSON
//
class Kotlin2Json01 {
    @Test
    fun example() {
        val userList = listOf(
            User(102, "jsmith", "P@ss", "John Smith"),
            User(103, "janed", "Pass1", "Jane Doe"),
        )

        val mapper = jacksonObjectMapper()
        val actual = mapper.writeValueAsString(userList)
        val expected = "[{\"id\":102,\"username\":\"jsmith\",\"password\":\"P@ss\",\"fullName\":\"John Smith\"},{\"id\":103,\"username\":\"janed\",\"password\":\"Pass1\",\"fullName\":\"Jane Doe\"}]"
        assertEquals(expected, actual)
    }
}