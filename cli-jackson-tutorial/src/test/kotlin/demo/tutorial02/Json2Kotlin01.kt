package demo.tutorial02

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import kotlin.test.assertEquals


//
// Deserialize(JSON 文字列 -> Object)
// https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
//
// JSON to User
//
class Json2Kotlin01 {
    @Test
    fun example() {
        val mapper = jacksonObjectMapper()
        val jsonString = """
            {
              "id":101,
              "username":"admin",
              "password":"Admin123",
              "fullName":"Best Admin"           
            }
        """.trimIndent()
        val actual = mapper.readValue<User>(jsonString) // or val userFromJsonWithType: User = mapper.readValue(jsonString)
        val expected = User(
            101,
            "admin",
            "Admin123",
            "Best Admin",
        )
        assertEquals(expected, actual)
    }
}